package duoc.cl.servicio;

import duoc.cl.conexion.ConexionDB;
import duoc.cl.dao.EntregaDAO;
import duoc.cl.dao.PedidoDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class EntregaServicio {
    private final EntregaDAO entregaDAO = new EntregaDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    public boolean registroEntregaEst(int id_pedido, int id_repartidor, LocalDate fecha,
                                      LocalTime hora, CondicionPedido nuevoEstado) throws SQLException {

       try(Connection con = ConexionDB.obtenerConexion()) {
           con.setAutoCommit(false);

           try {
               boolean okEntrega = entregaDAO.guardar(con, id_pedido, id_repartidor, fecha, hora);
               boolean okEstado = pedidoDAO.actualizarEstado(con, id_pedido, nuevoEstado);

               if(okEntrega && okEstado){
                   con.commit();
                   return true;
               }else {
                   con.rollback();
                   return false;
               }
           } catch (SQLException e) {
               con.rollback();
               throw e;
           }
       }
    }
}

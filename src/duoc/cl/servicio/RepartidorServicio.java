package duoc.cl.servicio;

import duoc.cl.dao.EntregaDAO;
import duoc.cl.dao.RepartidorDAO;
import duoc.cl.conexion.ConexionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class RepartidorServicio {
    private final EntregaDAO entregaDAO = new EntregaDAO();
    private final RepartidorDAO repartidorDAO = new RepartidorDAO();

    public boolean eliminarRepartidorEntregas(int idRepartidor) throws SQLException {
        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);

            try {
                entregaDAO.eliminarPorRepartidor(con, idRepartidor);
                boolean okRepartidor = repartidorDAO.eliminarRepartidor(con, idRepartidor);

                if (okRepartidor) {
                    con.commit();
                    return true;
                } else {
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

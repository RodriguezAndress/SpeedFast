package duoc.cl.dao;
import duoc.cl.conexion.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class EntregaDAO {

    public boolean guardar(int idPedido, int idRepartidor, LocalDate fecha, LocalTime hora) {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, idRepartidor);
            ps.setDate(3, Date.valueOf(fecha));
            ps.setTime(4, Time.valueOf(hora));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

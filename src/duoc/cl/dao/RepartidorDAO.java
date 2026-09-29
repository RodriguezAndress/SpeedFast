package duoc.cl.dao;

import duoc.cl.conexion.ConexionDB;

import java.sql.*;

public class RepartidorDAO {

    public boolean guardar(int id, String nombre) {
        String sql = "INSERT INTO repartidor (id, nombre) VALUES (?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, nombre);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
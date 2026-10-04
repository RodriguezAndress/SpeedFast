package duoc.cl.dao;

import duoc.cl.conexion.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean editarRepartidor(int id, String nombre){
        String sql = "UPDATE repartidor SET nombre = ? WHERE id = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarRepartidor(Connection con, int id) throws SQLException {

        String sql = "DELETE FROM repartidor WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
    public List<RepartidorDB> listarTodos(){
        List<RepartidorDB> repartidores = new ArrayList<>();
        String sql = "SELECT id, nombre FROM Repartidor";

        try(Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()){
                repartidores.add(new RepartidorDB(rs.getInt("id"), rs.getString("nombre")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return repartidores;
    }
}
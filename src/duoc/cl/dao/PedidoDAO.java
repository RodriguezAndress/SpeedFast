package duoc.cl.dao;

import duoc.cl.model.*;
import duoc.cl.servicio.CondicionPedido;
import duoc.cl.conexion.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static duoc.cl.servicio.CondicionPedido.PENDIENTE;

public class PedidoDAO {

    public boolean guardarDirecto(int id, String direccion, String tipo) {
        String sql = "INSERT INTO pedido (id, direccion, tipo, estado) VALUES (?, ?, ?, 'PENDIENTE')";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, direccion);
            ps.setString(3, tipo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editarPedido(int id, String direccion, String tipo, String estado){
        String sql = "UPDATE pedido SET direccion = ?, tipo = ?, estado =? WHERE id = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, direccion);
            ps.setString(2, tipo);
            ps.setString(3, estado);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarEstado(Connection con, int idPedido, CondicionPedido nuevoEstado) {
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, idPedido);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPedido(int idPedido) throws SQLException, IllegalStateException {

            String sqlEstado = "SELECT estado FROM pedido WHERE id = ?";
            try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement psEstado = con.prepareStatement(sqlEstado)) {
                psEstado.setInt(1, idPedido);
                try (ResultSet rs = psEstado.executeQuery()) {
                    if (!rs.next()) {
                        throw new IllegalStateException("Pedido no encontrado");
                    }
                    String estadoActual = rs.getString("estado");
                    if (!estadoActual.equals(PENDIENTE.name())) {
                        throw new IllegalStateException("El pedido no se puede eliminar " + estadoActual );
                    }
                }
            }
        String sqlDelete = "DELETE FROM pedido WHERE id = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sqlDelete)) {

            ps.setInt(1, idPedido);
            return ps.executeUpdate() > 0;

        }
    }

    public List<Object[]> listarTodos() {
        List<Object[]> pedidos = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedido";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pedidos.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }
}

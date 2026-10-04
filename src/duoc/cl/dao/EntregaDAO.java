package duoc.cl.dao;
import duoc.cl.conexion.ConexionDB;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static duoc.cl.servicio.CondicionPedido.EN_REPARTO;

public class EntregaDAO {

    public boolean guardar(Connection con, int idPedido, int idRepartidor, LocalDate fecha, LocalTime hora) {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {

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
    public boolean editarEntrega(int idPedido, LocalTime hora){
        String sql = "UPDATE entrega SET hora = ? WHERE id_pedido = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTime(1, Time.valueOf(hora));
            ps.setInt(2, idPedido);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarEntrega(int idPedido) throws SQLException, IllegalStateException {
        String sqlEstado = "SELECT estado FROM pedido WHERE id = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement psEstado = con.prepareStatement(sqlEstado)) {

            psEstado.setInt(1, idPedido);
            try (ResultSet rs = psEstado.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalStateException("Pedido no encontrado");
                }
                String estadoActual = rs.getString("estado");
                if (!estadoActual.equals(EN_REPARTO.name())) {
                    throw new IllegalStateException("La entrega no se puede eliminar porque ya está en " + estadoActual );
                }
            }

        }

        String sqlDelete = "DELETE FROM entrega WHERE id_pedido = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sqlDelete)) {

            ps.setInt(1, idPedido);
            return ps.executeUpdate() > 0;

        }
    }
    public List<Object[]> listarEntregasConDetalle() {
        List<Object[]> entregas = new ArrayList<>();
        String sql = "SELECT p.id, p.direccion, p.tipo, p.estado, r.nombre " +
                "FROM pedido p " +
                "JOIN entrega e ON p.id = e.id_pedido " +
                "JOIN repartidor r ON e.id_repartidor = r.id";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                entregas.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getString("estado"),
                        rs.getString("nombre")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entregas;
    }
    public boolean eliminarPorRepartidor(Connection con, int idRepartidor) throws SQLException {
        String sql = "DELETE FROM entrega WHERE id_repartidor = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRepartidor);
            ps.executeUpdate();
            return true;
        }
    }
}

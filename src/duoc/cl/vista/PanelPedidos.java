package duoc.cl.vista;

import duoc.cl.dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class PanelPedidos extends JPanel {
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private DefaultTableModel modeloTabla;
    private JTable tablaPedidos;
    private int idPedidoSeleccionado = -1;

    public PanelPedidos() {
        setLayout(new BorderLayout());
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNuevo  = new JButton("Nuevo");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Dirección", "Tipo", "Estado"}, 0);
        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.getSelectionModel().addListSelectionListener(e ->{
            if(!e.getValueIsAdjusting() && tablaPedidos.getSelectedRow() != 1) {
                idPedidoSeleccionado = (int) modeloTabla.getValueAt(tablaPedidos.getSelectedRow(), 0);
            }
        });
        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        btnNuevo.addActionListener(e -> registrarPedido());
        btnEditar.addActionListener(e -> editarPedido());
        btnEliminar.addActionListener(e -> eliminarPedido());
        btnActualizar.addActionListener(e -> cargarPedidos());

        cargarPedidos();
    }
    private void registrarPedido() {
        try{
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del Pedido:"));
            String direccion = JOptionPane.showInputDialog(this, "Dirección de entrega:");

            String[] tipos = {"Comida", "Encomienda", "Express"};
            String tipo = (String) JOptionPane.showInputDialog(this, "Tipo de pedido:",
                    "Tipo", JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

            if(direccion == null || tipo == null) return;
            boolean ok = pedidoDAO.guardarDirecto(id, direccion, tipo);
            mostrarResultado(ok, "Pedido Registrado.", "No se pudo registrar el pedido");
            cargarPedidos();

        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "El ID debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void editarPedido() {
        if(idPedidoSeleccionado == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la lista", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int fila = tablaPedidos.getSelectedRow();
        String direccionActual = (String) modeloTabla.getValueAt(fila, 1);
        String tipoActual = (String) modeloTabla.getValueAt(fila, 2);
        String estadoActual = (String) modeloTabla.getValueAt(fila, 3);

        String direccion = JOptionPane.showInputDialog(this, "Dirección de entrega:", direccionActual);
        if (direccion == null) return;

        String[] tipos = {"Comida", "Encomienda", "Express"};
        String tipo = (String) JOptionPane.showInputDialog(this, "Tipo de pedido:",
                "Tipo", JOptionPane.QUESTION_MESSAGE, null, tipos, tipoActual);
        if (tipo == null) return;

        String[] estados = {"PENDIENTE", "EN_REPARTO", "ENTREGADO"};
        String estado = (String) JOptionPane.showInputDialog(this, "Estado:",
                "Estado", JOptionPane.QUESTION_MESSAGE, null, estados, estadoActual);
        if (estado == null) return;

        boolean ok = pedidoDAO.editarPedido(idPedidoSeleccionado, direccion, tipo, estado);
        mostrarResultado(ok, "Pedido actualizado.", "No se pudo actualizar el pedido.");
        cargarPedidos();
    }

    private void eliminarPedido() {
        if (idPedidoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar el pedido " + idPedidoSeleccionado + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmar != JOptionPane.YES_OPTION) return;

        try {
            boolean ok = pedidoDAO.eliminarPedido(idPedidoSeleccionado);
            mostrarResultado(ok, "Pedido eliminado.", "No se pudo eliminar el pedido.");
            cargarPedidos();

        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se puede eliminar", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error de base de datos al eliminar." + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        for (Object[] fila : pedidoDAO.listarTodos()) {
            modeloTabla.addRow(fila);
        }
    }

    private void mostrarResultado(boolean ok, String mensajeOk, String mensajeError) {
        JOptionPane.showMessageDialog(this, ok ? mensajeOk : mensajeError,
                ok ? "Éxito" : "Error", ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}
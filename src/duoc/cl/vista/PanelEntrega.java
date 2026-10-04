package duoc.cl.vista;

import duoc.cl.dao.EntregaDAO;
import duoc.cl.dao.RepartidorDAO;
import duoc.cl.dao.RepartidorDB;
import duoc.cl.servicio.CondicionPedido;
import duoc.cl.servicio.EntregaServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class PanelEntrega extends JPanel {
    private final EntregaDAO entregaDAO = new EntregaDAO();
    private final RepartidorDAO repartidorDAO = new RepartidorDAO();
    private final EntregaServicio entregaServicio = new EntregaServicio();

    private DefaultTableModel modeloTabla;
    private JTable tablaEntregas;
    private int idPedidoSeleccionado = -1;

    public PanelEntrega() {
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Dirección", "Tipo", "Estado", "Repartidor"}, 0);
        tablaEntregas = new JTable(modeloTabla);

        tablaEntregas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaEntregas.getSelectedRow() != -1) {
                idPedidoSeleccionado = (int) modeloTabla.getValueAt(tablaEntregas.getSelectedRow(), 0);
            }
        });

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(tablaEntregas), BorderLayout.CENTER);

        btnNuevo.addActionListener(e -> registrarEntrega());
        btnEditar.addActionListener(e -> editarEntrega());
        btnEliminar.addActionListener(e -> eliminarEntrega());
        btnActualizar.addActionListener(e -> cargarEntregas());

        cargarEntregas();
    }
    private void registrarEntrega() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Registrar Entrega", true);
        dialog.setSize(350, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txtIdPedido = new JTextField(12);
        JComboBox<RepartidorDB> cbRepartidor = new JComboBox<>();
        for (RepartidorDB r : repartidorDAO.listarTodos()) {
            cbRepartidor.addItem(r);
        }
        JTextField txtFecha = new JTextField(12);
        JTextField txtHora = new JTextField(12);
        JComboBox<String> cbEstado = new JComboBox<>(new String[]{"PENDIENTE", "EN_REPARTO", "ENTREGADO"});

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("ID del Pedido:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtIdPedido, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Repartidor:"), gbc);
        gbc.gridx = 1;
        dialog.add(cbRepartidor, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Fecha (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        dialog.add(txtFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Hora (HH:mm:ss):"), gbc);
        gbc.gridx = 1;
        dialog.add(txtHora, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        dialog.add(cbEstado, gbc);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        gbc.gridx = 0; gbc.gridy = 5;
        dialog.add(btnGuardar, gbc);
        gbc.gridx = 1;
        dialog.add(btnCancelar, gbc);

        btnCancelar.addActionListener(e -> dialog.dispose());

        btnGuardar.addActionListener(e -> {
            try {
                int idPedido = Integer.parseInt(txtIdPedido.getText());
                RepartidorDB repartidor = (RepartidorDB) cbRepartidor.getSelectedItem();
                if (repartidor == null) {
                    JOptionPane.showMessageDialog(dialog, "No hay repartidores disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalDate fecha = LocalDate.parse(txtFecha.getText());
                LocalTime hora = LocalTime.parse(txtHora.getText());
                String estado = (String) cbEstado.getSelectedItem();

                boolean ok = entregaServicio.registroEntregaEst(
                        idPedido, repartidor.getId(), fecha, hora, CondicionPedido.valueOf(estado));

                if (ok) {
                    JOptionPane.showMessageDialog(dialog, "Entrega registrada.");
                    dialog.dispose();
                    cargarEntregas();
                } else {
                    JOptionPane.showMessageDialog(dialog, "No se pudo registrar la entrega.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "El ID del pedido debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Formato de fecha u hora inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }
    private void editarEntrega(){
        if (idPedidoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una entrega de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LocalTime hora = LocalTime.parse(JOptionPane.showInputDialog(this, "Nueva hora (HH:mm:ss):"));
            boolean ok = entregaDAO.editarEntrega(idPedidoSeleccionado, hora);
            mostrarResultado(ok, "Entrega actualizada.", "No se pudo actualizar la entrega.");
            cargarEntregas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato de hora inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void eliminarEntrega(){
        if (idPedidoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una entrega de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar la entrega del pedido " + idPedidoSeleccionado + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmar != JOptionPane.YES_OPTION) return;

        try {
            boolean ok = entregaDAO.eliminarEntrega(idPedidoSeleccionado);
            mostrarResultado(ok, "Entrega eliminada.", "No se pudo eliminar la entrega.");
            cargarEntregas();

        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se puede eliminar", JOptionPane.WARNING_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de base de datos al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void cargarEntregas() {
        modeloTabla.setRowCount(0);
        for (Object[] fila : entregaDAO.listarEntregasConDetalle()) {
            modeloTabla.addRow(fila);
        }
    }

    private void mostrarResultado(boolean ok, String mensajeOk, String mensajeError) {
        JOptionPane.showMessageDialog(this, ok ? mensajeOk : mensajeError,
                ok ? "Éxito" : "Error", ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}

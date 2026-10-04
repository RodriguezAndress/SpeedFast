package duoc.cl.vista;

import duoc.cl.dao.RepartidorDAO;
import duoc.cl.dao.RepartidorDB;
import duoc.cl.servicio.RepartidorServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PanelRepartidor extends JPanel {
    private final RepartidorDAO repartidorDAO = new RepartidorDAO();
    private DefaultTableModel modeloTabla;
    private JTable tablaRepartidores;
    private final RepartidorServicio repartidorServicio = new RepartidorServicio();
    private int idRepartidorSeleccionado = -1;

    public PanelRepartidor() {
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

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0);
        tablaRepartidores = new JTable(modeloTabla);

        tablaRepartidores.getSelectionModel().addListSelectionListener(e->{
            if (!e.getValueIsAdjusting() && tablaRepartidores.getSelectedRow() != -1) {
                idRepartidorSeleccionado = (int) modeloTabla.getValueAt(tablaRepartidores.getSelectedRow(), 0);
            }
        });

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(tablaRepartidores), BorderLayout.CENTER);

        btnNuevo.addActionListener(e -> registrarRepartidor());
        btnEditar.addActionListener(e -> editarRepartidor());
        btnEliminar.addActionListener(e -> eliminarRepartidor());
        btnActualizar.addActionListener(e -> cargarRepartidores());

        cargarRepartidores();
    }
    private void registrarRepartidor(){
        try{
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del repartidor:"));
            String nombre = JOptionPane.showInputDialog(this, "Nombre del repartidor:");
            if (nombre == null) return;

            boolean ok = repartidorDAO.guardar(id, nombre);
            mostrarResultado(ok, "Repartidor registrado.", "No se pudo registrar el repartidor.");
            cargarRepartidores();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void editarRepartidor(){
        if(idRepartidorSeleccionado != -1){
            JOptionPane.showMessageDialog(this, "Selecciona un repartidor:", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int fila = tablaRepartidores.getSelectedRow();
        String nombreActual = (String) modeloTabla.getValueAt(fila,1);

        String nombre = JOptionPane.showInputDialog(this, "Nombre del repartidor:", nombreActual);
        if (nombre == null) return;

        boolean ok = repartidorDAO.editarRepartidor(idRepartidorSeleccionado, nombre);
        mostrarResultado(ok, "Repartidor actualizado.", "No se pudo actualizar el repartidor.");
        cargarRepartidores();
    }
    private void eliminarRepartidor() {
        if (idRepartidorSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un repartidor de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar al repartidor " + idRepartidorSeleccionado + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmar != JOptionPane.YES_OPTION) return;

        try {
            boolean ok = repartidorServicio.eliminarRepartidorEntregas(idRepartidorSeleccionado);
            mostrarResultado(ok, "Repartidor eliminado.", "No se pudo eliminar el repartidor.");
            cargarRepartidores();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error de base de datos al eliminar." + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void cargarRepartidores() {
        modeloTabla.setRowCount(0);
        List<RepartidorDB> repartidores = repartidorDAO.listarTodos();
        for (RepartidorDB r : repartidores) {
            modeloTabla.addRow(new Object[]{r.getId(), r.getNombre()});
        }
    }

    private void mostrarResultado(boolean ok, String mensajeOk, String mensajeError) {
        JOptionPane.showMessageDialog(this, ok ? mensajeOk : mensajeError,
                ok ? "Éxito" : "Error", ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}

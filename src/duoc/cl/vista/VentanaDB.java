package duoc.cl.vista;

import duoc.cl.dao.EntregaDAO;
import duoc.cl.dao.PedidoDAO;
import duoc.cl.dao.RepartidorDAO;
import duoc.cl.servicio.CondicionPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class  VentanaDB  extends JFrame {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final RepartidorDAO repartidorDAO = new RepartidorDAO();
    private final EntregaDAO entregaDAO = new EntregaDAO();

    private DefaultTableModel modeloTabla;
    private JTable tablaPedidos;

    public VentanaDB() {
        setTitle("SpeedFast — Gestión de Entregas");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5));
        JButton btnRegistrarPedido = new JButton("Registrar Pedido");
        JButton btnRegistrarRepartidor = new JButton("Registrar Repartidor");
        JButton btnRegistrarEntrega = new JButton("Registrar Entrega");
        JButton btnActualizar = new JButton("Actualizar");

        panelBotones.add(btnRegistrarPedido);
        panelBotones.add(btnRegistrarRepartidor);
        panelBotones.add(btnRegistrarEntrega);
        panelBotones.add(btnActualizar);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Dirección", "Tipo", "Estado"}, 0);
        tablaPedidos = new JTable(modeloTabla);

        add(panelBotones, BorderLayout.WEST);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        btnRegistrarPedido.addActionListener(e -> registrarPedido());
        btnRegistrarRepartidor.addActionListener(e -> registrarRepartidor());
        btnRegistrarEntrega.addActionListener(e -> registrarEntrega());
        btnActualizar.addActionListener(e -> cargarPedidos());

        cargarPedidos();
    }

    private void registrarPedido() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del pedido:"));
            String direccion = JOptionPane.showInputDialog(this, "Dirección de entrega:");

            String[] tipos = {"Comida", "Encomienda", "Express"};
            String tipo = (String) JOptionPane.showInputDialog(this, "Tipo de pedido:",
                    "Tipo", JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

            if (direccion == null || tipo == null) return;

            String sql = "INSERT INTO pedido (id, direccion, tipo, estado) VALUES (?, ?, ?, 'PENDIENTE')";
            boolean ok = pedidoDAO.guardarDirecto(id, direccion, tipo);

            mostrarResultado(ok, "Pedido registrado.", "No se pudo registrar el pedido.");
            cargarPedidos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarRepartidor() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del repartidor:"));
            String nombre = JOptionPane.showInputDialog(this, "Nombre del repartidor:");
            if (nombre == null) return;

            boolean ok = repartidorDAO.guardar(id, nombre);
            mostrarResultado(ok, "Repartidor registrado.", "No se pudo registrar el repartidor.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarEntrega() {
        try {
            int idPedido = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del pedido:"));
            int idRepartidor = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del repartidor:"));
            LocalDate fecha = LocalDate.parse(JOptionPane.showInputDialog(this, "Fecha (yyyy-MM-dd):"));
            LocalTime hora = LocalTime.parse(JOptionPane.showInputDialog(this, "Hora (HH:mm:ss):"));

            String[] estados = {"PENDIENTE", "EN_REPARTO", "ENTREGADO"};
            String estadoSeleccionado = (String) JOptionPane.showInputDialog(this, "Estado del pedido:",
                    "Estado", JOptionPane.QUESTION_MESSAGE, null, estados, estados[0]);

            if (estadoSeleccionado == null) return;

            boolean okEntrega = entregaDAO.guardar(idPedido, idRepartidor, fecha, hora);
            boolean okEstado = pedidoDAO.actualizarEstado(idPedido, CondicionPedido.valueOf(estadoSeleccionado));

            mostrarResultado(okEntrega && okEstado, "Entrega registrada.", "No se pudo registrar la entrega.");
            cargarPedidos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha u hora inválido.", "Error", JOptionPane.ERROR_MESSAGE);
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


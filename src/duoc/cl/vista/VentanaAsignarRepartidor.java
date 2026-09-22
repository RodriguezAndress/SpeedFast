package duoc.cl.vista;

import duoc.cl.controlador.PedidoControlador;
import duoc.cl.servicio.Pedido;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaAsignarRepartidor extends JFrame {
    private PedidoControlador controlador;
    private JComboBox<Pedido> cmbPedidos;
    private JLabel lblRepartidor;

    public VentanaAsignarRepartidor(PedidoControlador controlador) {
        this.controlador = controlador;

        setTitle("Asignar Repartidor");
        setSize(380,200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,10,8,10);
        gbc.anchor = GridBagConstraints.WEST;

        cmbPedidos = new JComboBox<>();
        cargarPedidosPendientes();

        lblRepartidor = new JLabel("—");

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new  JLabel("Pedido pendiente: "), gbc);
        gbc.gridx = 1;
        panelForm.add(cmbPedidos, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new  JLabel("Repartidor asignado: "), gbc);
        gbc.gridx = 1;
        panelForm.add(lblRepartidor, gbc);

        cmbPedidos.addActionListener(e -> actualizarRepartidor());
        actualizarRepartidor();

        JButton btnAgignar = new JButton("Asignar");
        btnAgignar.addActionListener(e -> asignar());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnAgignar);

        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }
    private void cargarPedidosPendientes() {
        cmbPedidos.removeAllItems();
        List<Pedido> pendientes = controlador.obtenerPedidosPendientes();
        for(Pedido p : pendientes){
            cmbPedidos.addItem(p);
        }
    }
    private void actualizarRepartidor(){
        Pedido selected = (Pedido) cmbPedidos.getSelectedItem();
        lblRepartidor.setText(selected != null ? selected.asignarRepartidor() : "-—-");
    }
    private void asignar(){
        Pedido selected = (Pedido) cmbPedidos.getSelectedItem();
        if(selected == null){
            JOptionPane.showMessageDialog(this, "Sin pedidos pendientes.",
                    "Aviso",  JOptionPane.WARNING_MESSAGE);
            return;
        }
        controlador.asignarRepartidor(selected.getIdPedido());
        JOptionPane.showMessageDialog(this, "Repartidor asignado.");

        cargarPedidosPendientes();
        actualizarRepartidor();
    }
}

package duoc.cl.vista;
import duoc.cl.controlador.PedidoControlador;
import duoc.cl.model.PedidoComida;
import duoc.cl.model.PedidoEncomienda;
import duoc.cl.model.PedidoExpress;
import duoc.cl.servicio.Pedido;
import duoc.cl.servicio.CondicionPedido;

import javax.swing.*;
import java.awt.*;


public class VentanaRegistrarPedido extends JFrame {

    private PedidoControlador controlador;
    private JTextField txtIdPedido, txtDireccion;
    private JComboBox<String> cbTipo;

    public VentanaRegistrarPedido(PedidoControlador controlador) {
        this.controlador = controlador;

        setTitle("Registrar Pedido");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Solo cierra la venta que esta usando y la principal sigue abierta
        setResizable(false);

        JPanel panelFormu =  new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        txtIdPedido = new JTextField(15);
        txtDireccion = new JTextField(15);
        cbTipo = new JComboBox<>(new String[]{"Comida", "Express", "Encomienda"});

        gbc.gridx = 0; gbc.gridy = 0;
        panelFormu.add(new JLabel("ID del Pedido"), gbc);
        gbc.gridx = 1;
        panelFormu.add(txtIdPedido, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormu.add(new JLabel("Direccion del Pedido"), gbc);
        gbc.gridx = 1;
        panelFormu.add(txtDireccion, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelFormu.add(new JLabel("Tipo del Pedido"), gbc);
        gbc.gridx = 1;
        panelFormu.add(cbTipo, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnGuardar.addActionListener(e -> registrarPedido());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);

       add(panelFormu, BorderLayout.CENTER);
       add(panelBotones, BorderLayout.SOUTH);
       setLocationRelativeTo(null);
    }
    private void limpiarCampos() {
        txtIdPedido.setText("");
        txtDireccion.setText("");
        cbTipo.setSelectedIndex(0);
    }
    private void registrarPedido(){
        try{
            int idPedido = Integer.parseInt(txtIdPedido.getText());
            String direccion = txtDireccion.getText();
            String tipo = (String)  cbTipo.getSelectedItem();

            if(direccion.isBlank()){
                JOptionPane.showMessageDialog(this, "La dirección no puede estar vacia.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Pedido nuevoPedido;
            switch (tipo){
                case "Comida":
                    nuevoPedido = new PedidoComida(0, idPedido, "", direccion,
                            0.0, CondicionPedido.PENDIENTE, "", 0);
                    break;
                case "Express":
                    nuevoPedido = new PedidoExpress(0, idPedido, "", direccion, 0.0,
                                CondicionPedido.PENDIENTE, "");
                    break;
                case "Encomienda":
                    nuevoPedido = new PedidoEncomienda(0, idPedido, "", direccion,
                             0.0, CondicionPedido.PENDIENTE, 0, 0);
                    break;
                default:
                    return;
            }
            controlador.agregarPedido(nuevoPedido);
            JOptionPane.showMessageDialog(this, "Pedido registrado exitosamente.");
            limpiarCampos();
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "El ID del pedido debe ser numerico.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

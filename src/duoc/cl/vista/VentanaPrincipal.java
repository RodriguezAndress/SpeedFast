package duoc.cl.vista;
import duoc.cl.controlador.PedidoControlador;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private PedidoControlador controlador;

    public VentanaPrincipal(PedidoControlador controlador) {
        this.controlador = controlador;


    setTitle("SpeedFast -—- Menú Principal ");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout( new GridLayout(4, 1, 10, 10));

    JButton btnRegistrar = new JButton("Registrar Pedido");
    JButton btnLista = new JButton("Listar Pedidos");
    JButton btnAsignar = new JButton("Asignar Repartidor");
    JButton btnIniciar = new JButton("Iniciar Entregas");

    btnRegistrar.addActionListener(e -> new VentanaRegistrarPedido(controlador).setVisible(true));
    btnLista.addActionListener(e -> new VentanaListaPedidos(controlador).setVisible(true));
    btnAsignar.addActionListener(e -> new VentanaAsignarRepartidor(controlador).setVisible(true));
    btnIniciar.addActionListener( e -> new VentanaIniciarEntrega(controlador).setVisible(true));

    add(btnRegistrar);
    add(btnLista);
    add(btnAsignar);
    add(btnIniciar);

    setLocationRelativeTo(null);
    setVisible(true);
    }
}
package duoc.cl.vista;

import javax.swing.*;
import java.awt.*;

public class  VentanaDB  extends JFrame {
    private CardLayout cardLayout;
    private JPanel panelCentro;

    public VentanaDB() {
        setTitle("SpeedFast — Sistema de Gestión de Entregas");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Botones laterales
        JPanel panelNav = new JPanel(new GridLayout(4, 1, 5, 5));
        JButton btnRepartidores = new JButton("Repartidores");
        JButton btnPedidos = new JButton("Pedidos");
        JButton btnEntregas = new JButton("Entregas");
        JButton btnSalir = new JButton("Salir");

        panelNav.add(btnRepartidores);
        panelNav.add(btnPedidos);
        panelNav.add(btnEntregas);
        panelNav.add(btnSalir);

        //Centro
        cardLayout = new CardLayout();
        panelCentro = new JPanel(cardLayout);

        panelCentro.add(new PanelRepartidor(), "REPARTIDORES");
        panelCentro.add(new PanelPedidos(), "PEDIDOS");
        panelCentro.add(new PanelEntrega(), "ENTREGAS");

        add(panelNav,  BorderLayout.WEST);
        add(panelCentro, BorderLayout.CENTER);

        //Botones
        btnRepartidores.addActionListener(e -> cardLayout.show(panelCentro, "REPARTIDORES"));
        btnPedidos.addActionListener(e -> cardLayout.show(panelCentro, "PEDIDOS"));
        btnEntregas.addActionListener(e -> cardLayout.show(panelCentro, "ENTREGAS"));
        btnSalir.addActionListener(e -> System.exit(0));

    }

}


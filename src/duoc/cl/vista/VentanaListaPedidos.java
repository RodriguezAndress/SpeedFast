package duoc.cl.vista;

import duoc.cl.controlador.PedidoControlador;
import duoc.cl.servicio.CondicionPedido;
import duoc.cl.servicio.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {
    private PedidoControlador controlador;
    private DefaultTableModel modelo;

    public VentanaListaPedidos(PedidoControlador controlador) {
        this.controlador = controlador;

        setTitle("Lista Pedidos");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnas = {"ID", "Dirección", "Tipo", "Estado", "Repartidor"};
        modelo = new DefaultTableModel(columnas, 0);
        JTable table = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarTabla());
        add(btnActualizar, BorderLayout.SOUTH);

        cargarTabla();
        setLocationRelativeTo(null);
    }
    private void cargarTabla() {
        modelo.setRowCount(0);
        for(Pedido pedido : controlador.getPedidos()){
            String repartidor = pedido.getCondicionPedido() == CondicionPedido.PENDIENTE
                    ? "—"
                    : pedido.asignarRepartidor();
            Object[] fila = {
                    pedido.getIdPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getClass().getSimpleName(),
                    pedido.getCondicionPedido(),
                    repartidor
            };
            modelo.addRow(fila);
        }
    }

}

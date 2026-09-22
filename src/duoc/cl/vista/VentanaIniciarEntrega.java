package duoc.cl.vista;

import duoc.cl.controlador.PedidoControlador;
import duoc.cl.servicio.Pedido;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VentanaIniciarEntrega extends JFrame {
    private PedidoControlador controlador;
    private JTextArea areaLog;
    private JButton btnIniciar;

    public VentanaIniciarEntrega(PedidoControlador controlador) {
        this.controlador = controlador;

        setTitle("Iniciar Entrega");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        areaLog = new JTextArea();
        areaLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaLog);
        btnIniciar = new JButton("Iniciar");
        btnIniciar.addActionListener(e -> iniciarEntregas());

        add(scrollPane, BorderLayout.CENTER);
        add(btnIniciar, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }
    private void iniciarEntregas(){
        List<Pedido> pedidosReparto = controlador.obtenerPedidosReparto();
        if(pedidosReparto.isEmpty()){
            JOptionPane.showMessageDialog(this, "Sin pedidos en reparto",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        btnIniciar.setEnabled(false);
        areaLog.setText("");

        String[] nombreRepartidores = {"Camila", "Luis", "Pedro"};
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for(int i = 0; i < pedidosReparto.size(); i++){
            Pedido pedido = pedidosReparto.get(i);
            String nombreRepartidor = nombreRepartidores[i % nombreRepartidores.length];

            executor.submit(() -> {
                log("[Repartidor: " + nombreRepartidor + "] Entregando pedido #" + pedido.getIdPedido() + "...");
                try {
                    Thread.sleep((long) (Math.random() * 2000)+ 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                controlador.marcarEntegado(pedido.getIdPedido());
                log("[Repartidor: " + nombreRepartidor + "] Pedido #" + pedido.getIdPedido() + " entregado");

            });
        }
        executor.shutdown();
        new Thread(() -> {
            try{
                executor.awaitTermination(1, TimeUnit.MINUTES);
            }  catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(() -> {
                log("Todos los pedidos se entregaron correctamente.");
                btnIniciar.setEnabled(true);
            });
        }).start();
    }
    private void log(String mensaje){
        SwingUtilities.invokeLater(() -> areaLog.append(mensaje + "\n"));
    }
}

package duoc.cl.servicio;

import java.util.List;

public class Repartidor implements Runnable{
    private String nombre;
    private List<Pedido> order;


    public Repartidor(String nombre, List<Pedido> order) {
        this.nombre = nombre;
        this.order = order;
    }

    @Override
    public void run() {
        for (Pedido p : order) {
            String tipoPedido = p.getClass().getSimpleName();
            int id = p.getIdPedido();

            System.out.println("[Repartidor: " + nombre + "] Entregando " + tipoPedido + " # " + id + "...");

            try {
                Thread.sleep((long)(Math.random() * 2000) + 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("[Repartidor: " + nombre + "] Pedido #" + id + " entregado.");
        }

    }
}

package duoc.cl.servicio;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class ZonaDeCarga {
    private final BlockingQueue<Pedido> colaPedidos;

    public ZonaDeCarga() {
        colaPedidos = new LinkedBlockingQueue<>();
    }

    public void agregarPedido(Pedido pedido) {
         colaPedidos.offer(pedido);
        System.out.println("[Zona de Carga] Pedido # "+ pedido.getIdPedido() + " disponible");
    }

    public Pedido retirarPedido() throws InterruptedException {
        return colaPedidos.poll(2, TimeUnit.SECONDS);
    }
}

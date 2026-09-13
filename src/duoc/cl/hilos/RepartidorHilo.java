package duoc.cl.hilos;

import duoc.cl.servicio.CondicionPedido;
import duoc.cl.servicio.Pedido;
import duoc.cl.servicio.ZonaDeCarga;

public class RepartidorHilo implements Runnable{
    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    public RepartidorHilo(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {
        try {
            Pedido pedido;
            while ((pedido = zonaDeCarga.retirarPedido()) != null){
                System.out.println("[Repartidor " + nombre + " ] Retirando pedido # " + pedido.getIdPedido() + "...");

                pedido.setCondicionPedido(CondicionPedido.EN_REPARTO);
                System.out.println("[Repartidor " + nombre + " ] Entregando pedido # " + pedido.getIdPedido() + "...");

                Thread.sleep((long) (Math.random() * 2000) + 1000);

                pedido.setCondicionPedido(CondicionPedido.ENTREGADO);
                System.out.println("[Repartidor " + nombre + " ] pedido # " + pedido.getIdPedido() + " entregado ✓");
            }

        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}

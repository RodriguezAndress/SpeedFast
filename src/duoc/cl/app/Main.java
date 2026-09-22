import duoc.cl.controlador.PedidoControlador;
import duoc.cl.servicio.GestorPedidos;
import duoc.cl.servicio.Pedido;
import duoc.cl.servicio.ZonaDeCarga;
import duoc.cl.hilos.RepartidorHilo;
import duoc.cl.vista.VentanaPrincipal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

       /* System.out.println("...~~~ SpeedFast ~~~.....:::Sem1:::..");
        System.out.println("**** Sobrescritura y sobrecarga ****");
        List<Pedido> pedidos = GestorPedidos.pedidosSem1();

        System.out.println("\n**** Polimorfismo ****");
        for (Pedido p : pedidos) {
            p.estadoPedido();
        }

        System.out.println("\n...~~~ SpeedFast ~~~.....:::Sem2:::..");
        System.out.println("*** Resumen de Pedido ***");
        Pedido[] pedidosArray = GestorPedidos.pedidoSem2();
        for (Pedido pedido : pedidosArray) {
            pedido.mostrarResumen();
        }

        System.out.println("\n...~~~ SpeedFast ~~~.....:::Sem3:::..");
        List<Pedido> pedidosSem3 = GestorPedidos.pedidoSem3();
        for (Pedido ped : pedidosSem3) {

        }

        GestorPedidos.pedidoSem4();*/

       /*
        //Semana5
        ZonaDeCarga zonaDeCarga = GestorPedidos.datosZonaDeCarga();


        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new RepartidorHilo("Carmen", zonaDeCarga));
        executor.submit(new RepartidorHilo("Manuel", zonaDeCarga));
        executor.submit(new RepartidorHilo("Marco", zonaDeCarga));

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Todos los pedidos han sido entregados correctamente.");

        }*/

        //Semana6
        PedidoControlador controlador = new PedidoControlador();
        new VentanaPrincipal(controlador);

    }
}
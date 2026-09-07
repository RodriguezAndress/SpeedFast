package duoc.cl.speedFast.app;

import duoc.cl.speedFast.servicio.GestorPedidos;
import duoc.cl.speedFast.servicio.Pedido;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("...~~~ SpeedFast ~~~.....:::Sem1:::..");
        System.out.println("**** Sobrescritura y sobrecarga ****");
        List<Pedido> pedidos = GestorPedidos.pedidosSem1();

        System.out.println("\n**** Poliformismo ****");
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
    }
}
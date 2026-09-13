package duoc.cl.speedFast.app;

import duoc.cl.speedFast.model.PedidoComida;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Pedido> pedidos = new ArrayList<>();

        System.out.println("\n**** Sobrescritura y sobrecarga ****");
        System.out.println("~Pedido de Comida");
        PedidoComida pedidoCom = new PedidoComida(2353, "Juan José", "Sta Rosa 77", "Rosticeria", 45);
        pedidoCom.asignarRepartidor(true);
        pedidoCom.asignarRepartidor();
        pedidos.add(pedidoCom);

        System.out.println("\n ~Encomienda");
        PedidoEncomienda pedidoEnco = new PedidoEncomienda(9632, "Patrica Baez", "Procuro 400", 2.5,5);
        pedidoEnco.asignarRepartidor(true);
        pedidoEnco.asignarRepartidor();
        pedidos.add(pedidoEnco);

        System.out.println("\n ~Pedido Express");
        PedidoExpress pedidoExp = new PedidoExpress(2582, "Siena Paez", "Francisco 699", "Oxxo", 1.2);
        pedidoExp.asignarRepartidor(15);
        pedidoExp.asignarRepartidor();
        pedidos.add(pedidoExp);

        System.out.println("\n**** Poliformismo ****");
        for (Pedido p : pedidos) {
            p.asignarRepartidor();
        }

    }
}
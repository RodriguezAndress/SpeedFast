package duoc.cl.servicio;

import duoc.cl.model.PedidoComida;
import duoc.cl.model.PedidoEncomienda;
import duoc.cl.model.PedidoExpress;

import java.util.ArrayList;
import java.util.List;

public class GestorPedidos {
    public static List<Pedido> pedidosSem1() {
        List<Pedido> pedidos = new ArrayList<>();

        PedidoComida pedidoCom = new PedidoComida(2353, 0, "Juan José", "Sta Rosa 77", 1.5, CondicionPedido.PENDIENTE,"Rosticeria",45);
        pedidoCom.estadoPedido(true);
        pedidoCom.estadoPedido();
        pedidos.add(pedidoCom);

        System.out.println("\n ~Encomienda");
        PedidoEncomienda pedidoEnco = new PedidoEncomienda(9632, 0, "Patrica Baez", "Procuro 400", 2.5, CondicionPedido.PENDIENTE, 5, 30);
        pedidoEnco.estadoPedido(true);
        pedidoEnco.estadoPedido();
        pedidos.add(pedidoEnco);

        System.out.println("\n ~Pedido Express");
        PedidoExpress pedidoExp = new PedidoExpress(2582, 0, "Siena Paez", "Francisco 699", 1.2, CondicionPedido.PENDIENTE,"Oxxo");
        pedidoExp.estadoPedido(15);
        pedidoExp.estadoPedido();
        pedidos.add(pedidoExp);

        return pedidos;
    }


    public static Pedido[] pedidoSem2() {
        return new Pedido[]{
                new PedidoComida(4714, "Sta Elena 266", 3.3),
                new PedidoEncomienda(2580, "Makena 100", 5.2),
                new PedidoExpress(3424, "Alamos 141", 2.3)
        };
    }

    public static List<Pedido> pedidoSem3() {
        List<Pedido> pedidosLista = new ArrayList<>();

        PedidoComida pedComida = new PedidoComida(4714, "Sta Elena 266", 3.3);
        pedComida.mostrarResumen();
        pedComida.estadoPedido();
        pedComida.cancelar();
        pedComida.verHistorial();
        pedidosLista.add(pedComida);

        PedidoEncomienda pedEncomienda = new PedidoEncomienda(2580, "Makena 100", 5.2);
        pedEncomienda.mostrarResumen();
        pedEncomienda.estadoPedido();
        pedEncomienda.despachar();
        pedEncomienda.verHistorial();
        pedidosLista.add(pedEncomienda);

        PedidoExpress pedExpress = new PedidoExpress(4424, "Alamos 141", 2.3);
        pedExpress.mostrarResumen();
        pedExpress.calcularTiempoEntrega();
        pedExpress.estadoPedido();
        pedExpress.despachar();
        pedExpress.verHistorial();
        pedidosLista.add(pedExpress);

        return pedidosLista;

    }
    public static void pedidoSem4() throws InterruptedException{
        List<Pedido> pedidosCarmen = List.of(
                new PedidoComida(4714, "Sta Elena 266", 3.3)
        );
        List<Pedido> pedidoManuel = List.of(
                new PedidoExpress(102, "Oxxo", 1.5)
        );
        List<Pedido> pedidoMarco = List.of(
                new PedidoEncomienda(103, "Makena 100", 5.2)
        );

        Thread t1 = new Thread(new Repartidor("Carmen", pedidosCarmen));
        Thread t2 = new Thread(new Repartidor("Manuel", pedidoManuel));
        Thread t3 = new Thread(new Repartidor("Marco", pedidoMarco));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Todos los repartidores han finalizado sus entregas.");


    }
    public static ZonaDeCarga datosZonaDeCarga(){
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        zonaDeCarga.agregarPedido(new PedidoComida(301, "Salesianos 105",4.2));
        zonaDeCarga.agregarPedido(new PedidoExpress(507, "Makena 100", 5.2));
        zonaDeCarga.agregarPedido(new PedidoEncomienda(508, "Barcelona 10", 3.1));
        zonaDeCarga.agregarPedido(new PedidoComida(509,"Salvador 17", 5.5));
        zonaDeCarga.agregarPedido(new PedidoExpress(510, "Sta Victoria 55", 1.9));

        return zonaDeCarga;
    }
}

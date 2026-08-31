package duoc.cl.speedFast.servicio;

import duoc.cl.speedFast.model.PedidoComida;
import duoc.cl.speedFast.model.PedidoEncomienda;
import duoc.cl.speedFast.model.PedidoExpress;

import java.util.ArrayList;
import java.util.List;

public class GestorPedidos {
    public static List<Pedido> pedidosSem1() {
        List<Pedido> pedidos = new ArrayList<>();

        PedidoComida pedidoCom = new PedidoComida(2353, 0, "Juan José", "Sta Rosa 77", 1.5, "Rosticeria", 45);
        pedidoCom.estadoPedido(true);
        pedidoCom.estadoPedido();
        pedidos.add(pedidoCom);

        System.out.println("\n ~Encomienda");
        PedidoEncomienda pedidoEnco = new PedidoEncomienda(9632, 0, "Patrica Baez", "Procuro 400", 2.5, 5, 30);
        pedidoEnco.estadoPedido(true);
        pedidoEnco.estadoPedido();
        pedidos.add(pedidoEnco);

        System.out.println("\n ~Pedido Express");
        PedidoExpress pedidoExp = new PedidoExpress(2582, 0, "Siena Paez", "Francisco 699", 1.2, "Oxxo");
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

        PedidoEncomienda pedEncomineda = new PedidoEncomienda(2580, "Makena 100", 5.2);
        pedEncomineda.mostrarResumen();
        pedEncomineda.estadoPedido();
        pedEncomineda.despachar();
        pedEncomineda.verHistorial();
        pedidosLista.add(pedEncomineda);

        PedidoExpress pedExpress = new PedidoExpress(4424, "Alamos 141", 2.3);
        pedExpress.mostrarResumen();
        pedExpress.calcularTiempoEntrega();
        pedExpress.estadoPedido();
        pedExpress.despachar();
        pedExpress.verHistorial();
        pedidosLista.add(pedExpress);

        return pedidosLista;

    }
}

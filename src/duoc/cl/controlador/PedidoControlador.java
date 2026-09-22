package duoc.cl.controlador;

import duoc.cl.servicio.CondicionPedido;
import duoc.cl.servicio.GestorPedidos;
import duoc.cl.servicio.Pedido;
import duoc.cl.vista.TablaPedidos;

import java.awt.*;
import java.util.List;

public class PedidoControlador {

   private List<Pedido> pedidos;

    public PedidoControlador(){
        this.pedidos = GestorPedidos.pedidosTablaInterfaz();
    }
    public List<Pedido> getPedidos(){
        return pedidos;
    }
    public void agregarPedido(Pedido pedido){
        pedidos.add(pedido);
    }
    public List<Pedido> obtenerPedidosPendientes(){
        return pedidos.stream().filter(p -> p.getCondicionPedido() == CondicionPedido.PENDIENTE)
                .toList();
    }
    public void asignarRepartidor(int idPedido){
        for(Pedido p : pedidos){
            if(p.getIdPedido() == idPedido){
                p.setPrioridad(CondicionPedido.EN_REPARTO);
                return;
            }
        }
    }
    public List<Pedido> obtenerPedidosReparto(){
        return pedidos.stream().filter(p -> p.getCondicionPedido()
                == CondicionPedido.EN_REPARTO).toList();
    }
    public void marcarEntegado(int idPedido){
        for(Pedido p : pedidos){
            if(p.getIdPedido() == idPedido){
                p.setPrioridad(CondicionPedido.ENTREGADO);
                return;
            }
        }
    }
}

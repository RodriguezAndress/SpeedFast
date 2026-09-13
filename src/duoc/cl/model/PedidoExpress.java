package duoc.cl.model;

import duoc.cl.servicio.Pedido;
import duoc.cl.interfaces.IDespachable;
import duoc.cl.interfaces.IEstadoPedido;
import duoc.cl.interfaces.IRastreable;
import duoc.cl.servicio.CondicionPedido;

public class PedidoExpress extends Pedido implements IEstadoPedido, IDespachable, IRastreable {
    private String tienda;


    public PedidoExpress(int idCliente, int idPedido, String cliente, String direccionEntrega, double distanciaKm, CondicionPedido condicionPedido, String tienda) {
        super(idCliente, idPedido, cliente, direccionEntrega, distanciaKm, condicionPedido);
        this.tienda = tienda;
    }
    //Constructor sobrecarga con solo tres parametros para sem 2 y 3


    public PedidoExpress( int idPedido, String direccionEntrega, double distanciaKm) {
        this(0, idPedido, " ", direccionEntrega, distanciaKm, CondicionPedido.PENDIENTE,"");
    }

    public String getTienda() {return tienda;}
    public void setTienda(String tienda) {this.tienda = tienda;}

    @Override
    public void estadoPedido() {
        System.out.println("Su pedido express ya esta en camino...");
    }

    public void estadoPedido(int horaLimite){
        System.out.println("Pedido Express de: "+tienda+ " a: "+distanciaKm+ "km de distancia llegara hasta las "+horaLimite);
    }

    @Override
    public int calcularTiempoEntrega() {
        int tiempoInicial = 10;
        if(distanciaKm > 5){
            tiempoInicial = tiempoInicial + 5;
        }
        return tiempoInicial;
    }
    @Override
    public String asignarRepartidor() {
        String repartidor = "Manuel Mayer.";
        return repartidor;
    }
    @Override
    public void despachar() {
        System.out.println("\n Despachando pedido express: #"+idPedido);
        System.out.println(" —> Pedido express despachado.");
    }

    @Override
    public void verHistorial() {
        System.out.println("\n Historial:");
        System.out.println(getClass().getSimpleName() + " #" + idPedido + " Entregado por: " + asignarRepartidor());
    }
}


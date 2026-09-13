package duoc.cl.model;

import duoc.cl.interfaces.ICancelable;
import duoc.cl.interfaces.IEstadoPedido;
import duoc.cl.interfaces.IRastreable;
import duoc.cl.servicio.Pedido;
import duoc.cl.servicio.CondicionPedido;

public class PedidoComida extends Pedido implements IEstadoPedido, ICancelable, IRastreable {
    private String restaurante;
    private int tiempoPreparacion;

    public PedidoComida(int idCliente, int idPedido, String cliente, String direccionEntrega, double distanciaKm, CondicionPedido condicionPedido, String restaurante, int tiempoPreparacion) {
        super(idCliente, idPedido, cliente, direccionEntrega, distanciaKm, condicionPedido);
        this.restaurante = restaurante;
        this.tiempoPreparacion = tiempoPreparacion;
    }
    //Constructor sobrecarga de tres parametros arrays sem 2 y 3

    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm) {
        this(0, idPedido, "", direccionEntrega, distanciaKm, CondicionPedido.PENDIENTE,"", 0);
    }

    public String getRestaurante() {return restaurante;}
    public void setRestaurante(String restaurante) {this.restaurante = restaurante;}
    public int getTiempoPreparacion() {return tiempoPreparacion;}
    public void setTiempoPreparacion(int tiempoPreparacion) {this.tiempoPreparacion = tiempoPreparacion;}

    @Override
    public void estadoPedido() {
        System.out.println("Pedido despachado correctamente... ");
    }

    public void estadoPedido(boolean urgente){
        String tipoEntrega = urgente ? "Urgente" : "Normal";
        System.out.println("El restaurante: "+restaurante +" esta preparando su pedido ("+tipoEntrega+") en: "+tiempoPreparacion+ " minutos" );
    }

    @Override
    public int calcularTiempoEntrega() {
        int tiempoInicial = 15;
        int minutosKl = 2;

        int tiempoFinal = tiempoInicial + (int) (minutosKl * distanciaKm);

        return tiempoFinal;
    }

    @Override
    public void cancelar() {
        System.out.println("\n Cancelando pedido de comida: #"+idPedido);
        System.out.println("  —>Pedido de comida cancelado.");
    }

    @Override
    public String asignarRepartidor() {
        String repartidor = "Carmen Mancilla.";
        return repartidor;
    }

    @Override
    public void verHistorial() {
        System.out.println("\n Historial:");
        System.out.println(getClass().getSimpleName() + " #" + idPedido + " Cancelado ");
    }
}


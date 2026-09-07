package duoc.cl.model;

import duoc.cl.servicio.Pedido;
import duoc.cl.interfaces.IDespachable;
import duoc.cl.interfaces.IEstadoPedido;
import duoc.cl.interfaces.IRastreable;

public class PedidoEncomienda extends Pedido implements IEstadoPedido, IDespachable, IRastreable {
    private double peso;
    private double volumen;

    public PedidoEncomienda(int idCliente, int idPedido, String cliente, String direccion, double distanciaKm, double peso, double volumen) {
        super(idCliente, idPedido, cliente, direccion, distanciaKm);
        this.peso = peso;
        this.volumen = volumen;
    }
    //Constructor sobrecarga con solo tres parametros para arrays sem 2 y 3


    public PedidoEncomienda(int idPedido, String direccion, double distanciaKm) {
        this(0, idPedido, "", direccion, distanciaKm, 0, 0);
    }

    public double getPeso() {return peso;}
    public void setPeso(double peso) {this.peso = peso;}
    public double getVolumen() {return volumen;}
    public void setVolumen(double volumen) {this.volumen = volumen;}

    @Override
    public void estadoPedido() {
        System.out.println("Su encomienda va en camino...");
    }

    public void estadoPedido(boolean prioritario){
        String tipoEntrega = prioritario ? "Prioritaria" : "Normal";
        System.out.println("Su encomienda es de importancia: "+tipoEntrega);
    }

    @Override
    public int calcularTiempoEntrega() {
        int tiempoInicial = 20;
        double minutosKl = 1.5;

        double minutoExtra = minutosKl * distanciaKm;
        int tiempoFinal = tiempoInicial + (int) Math.round(minutoExtra);

        return tiempoFinal;
    }

    @Override
    public void despachar() {
        System.out.println("\n Despachando encomienda: #"+idPedido);
        System.out.println(" —> Pedido de encomienda despachado.");
    }

    @Override
    public String asignarRepartidor() {
        String repartidor = "Marco Yañez.";
        return repartidor;
    }

    @Override
    public void verHistorial() {
        System.out.println("\n Historial:");
        System.out.println(getClass().getSimpleName() + " #" + idPedido + " Entregado por: " + asignarRepartidor());
    }
}


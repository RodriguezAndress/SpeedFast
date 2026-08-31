package duoc.cl.speedFast.model;

import duoc.cl.speedFast.interfaces.IRepartidor;

public class PedidoComida extends Pedido implements IRepartidor {
    private String restaurante;
    private int tiempoPreparacion;

    public PedidoComida(int idCliente, String cliente, String direccion, String restaurante, int tiempoPreparacion) {
        super(idCliente, cliente, direccion);
        this.restaurante = restaurante;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getRestaurante() {return restaurante;}

    public void setRestaurante(String restaurante) {this.restaurante = restaurante;}

    public int getTiempoPreparacion() {return tiempoPreparacion;}

    public void setTiempoPreparacion(int tiempoPreparacion) {this.tiempoPreparacion = tiempoPreparacion;}

    @Override
    public void asignarRepartidor() {
        System.out.println("Su pedido de comida ya esta en camino...");
    }
    public void asignarRepartidor(boolean urgente){
        String tipoEntrega = urgente ? "Urgente" : "Normal";
        System.out.println("El restaurante: "+restaurante +" esta preparando su pedido ("+tipoEntrega+") en: "+tiempoPreparacion+ " minutos" );
    }
}

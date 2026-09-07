package duoc.cl.speedFast.model;

import duoc.cl.speedFast.interfaces.IRepartidor;

public class PedidoExpress extends Pedido implements IRepartidor {
    private String tienda;
    private double distancia;

    public PedidoExpress(int idCliente, String cliente, String direccion, String tienda, double distancia) {
        super(idCliente, cliente, direccion);
        this.tienda = tienda;
        this.distancia = distancia;
    }

    public String getTienda() {return tienda;}
    public void setTienda(String tienda) {this.tienda = tienda;}
    public double getDistancia() {return distancia;}
    public void setDistancia(double distancia) {this.distancia = distancia;}

    @Override
    public void asignarRepartidor() {
        System.out.println("Su pedido express ya esta en camino...");
    }
    public void asignarRepartidor(int horaLimite){
        System.out.println("Pedido Express de: "+tienda+ " a: "+distancia+ "km de distancia llegara hasta las "+horaLimite);
    }
}

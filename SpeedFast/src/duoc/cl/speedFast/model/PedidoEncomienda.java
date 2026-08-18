package duoc.cl.speedFast.model;

import duoc.cl.speedFast.interfaces.IRepartidor;

public class PedidoEncomienda extends Pedido implements IRepartidor {
    private double peso;
    private double volumen;

    public PedidoEncomienda(int idCliente, String cliente, String direccion, double peso, double volumen) {
        super(idCliente, cliente, direccion);
        this.peso = peso;
        this.volumen = volumen;
    }

    public double getPeso() {return peso;}
    public void setPeso(double peso) {this.peso = peso;}
    public double getVolumen() {return volumen;}
    public void setVolumen(double volumen) {this.volumen = volumen;}

    @Override
    public void asignarRepartidor() {
        System.out.println("Su encomienda va en camino...");
    }
    public void asignarRepartidor(boolean prioritario){
        String tipoEntrega = prioritario ? "Prioritaria" : "Normal";
        System.out.println("Su encomienda es de importancia: "+tipoEntrega);
    }

}

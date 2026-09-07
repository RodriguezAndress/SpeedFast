package duoc.cl.servicio;


import duoc.cl.interfaces.IEstadoPedido;

public abstract class Pedido implements IEstadoPedido {
    protected int idCliente;
    protected int idPedido;
    protected String cliente;
    protected String direccion;
    protected double distanciaKm;

    public Pedido(int idCliente, int idPedido, String cliente, String direccion, double distanciaKm) {
        this.idCliente = idCliente;
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.direccion = direccion;
        this.distanciaKm = distanciaKm;
    }
    public Pedido(int idPedido){
        if(idPedido <= 0){
            throw new IllegalArgumentException("El pedido no puede ser cero");
        }
        this.idPedido = idPedido;
    }

    public int getIdPedido() {return idPedido;}

    public void mostrarResumen() {
        System.out.println("\n" + getClass().getSimpleName());
        System.out.println(getClass().getSimpleName() + " " + idPedido);
        System.out.println("Direccion: " + direccion);
        System.out.println("Distancia: " + distanciaKm + ("Km"));
        System.out.println("Repartidor asignado: " + asignarRepartidor());
        System.out.println("Su pedido llegará en: " + calcularTiempoEntrega() +(" Min.") );
    }

    public abstract int calcularTiempoEntrega();
    public abstract String asignarRepartidor();

    @Override
    public void estadoPedido() {
        System.out.println("Asignando Repartidor...");
    }

}


package duoc.cl.servicio;


public abstract class Pedido implements Comparable<Pedido> {
    protected int idCliente;
    protected int idPedido;
    protected String cliente;
    protected String direccionEntrega;
    protected double distanciaKm;
    protected CondicionPedido condicionPedido;

    public Pedido(int idCliente, int idPedido, String cliente, String direccionEntrega, double distanciaKm,  CondicionPedido condicionPedido) {
        this.idCliente = idCliente;
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.condicionPedido = condicionPedido;
    }
    public Pedido(int idPedido){
        if(idPedido <= 0){
            throw new IllegalArgumentException("El pedido no puede ser cero");
        }
        this.idPedido = idPedido;
    }

    public int getIdPedido() {return idPedido;}
    public String getDireccionEntrega() {return direccionEntrega;}

    //Cambio el nombre de Estado-EstadoPedido por CondicionPedido porque el nombre anterior ya lo estaba ocupando
    public CondicionPedido getCondicionPedido() {return condicionPedido;}
    public void setPrioridad(CondicionPedido condicionPedido) {this.condicionPedido = condicionPedido;}


    public void mostrarResumen() {
        System.out.println("\n" + getClass().getSimpleName());
        System.out.println(getClass().getSimpleName() + " " + idPedido);
        System.out.println("Direccion: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + ("Km"));
        System.out.println("Repartidor asignado: " + asignarRepartidor());
        System.out.println("Su pedido llegará en: " + calcularTiempoEntrega() +(" Min.") );
    }

    public abstract int calcularTiempoEntrega();
    public abstract String asignarRepartidor();

    @Override
    public int compareTo(Pedido other) {
        return this.condicionPedido.ordinal() - other.condicionPedido.ordinal() ;
    }

    @Override
    public String toString() {
        return "#" + idPedido + " — " + direccionEntrega;
    }
}


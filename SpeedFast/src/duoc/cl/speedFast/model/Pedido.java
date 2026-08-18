package duoc.cl.speedFast.model;
import duoc.cl.speedFast.interfaces.IRepartidor;

public class Pedido implements IRepartidor {
    private int idCliente;
    private String cliente;
    private String direccion;

    public Pedido(int idCliente, String cliente, String direccion) {
        this.idCliente = idCliente;
        this.cliente = cliente;
        this.direccion = direccion;
    }

    public int getIdCliente() {return idCliente;}

    public void setIdCliente(int idCliente) {this.idCliente = idCliente;}

    public String getCliente() {return cliente;}

    public void setCliente(String cliente) {this.cliente = cliente;}

    public String getDireccion() {return direccion;}

    public void setDireccion(String direccion) {this.direccion = direccion;}


    @Override
    public void asignarRepartidor() {
        System.out.println("Asignando Repartidor...");
    }
}

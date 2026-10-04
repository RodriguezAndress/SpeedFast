package duoc.cl.dao;

public class RepartidorDB {
    private int id;
    private String nombre;

    public RepartidorDB(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public int getId() {return id;}
    public String getNombre() { return nombre;}

    @Override
    public String toString() {
        return nombre;
    }

}

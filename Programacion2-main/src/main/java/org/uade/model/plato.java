package main.java.org.uade.model;

public class plato {
    private final int id;
    private final String nombre;

    public plato(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
}

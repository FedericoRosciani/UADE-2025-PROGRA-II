package main.java.org.uade.model;

public class repartidor {

    private final int id;
    private final String nombre;
    private boolean disponible = true;
    private int entregas = 0;

    // 🔹 Nueva propiedad: ubicación actual (zona del grafo)
    private int ubicacionActual = 1; // Por defecto Palermo (zona 1)

    public repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public String getNombre() { return nombre; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean d) { this.disponible = d; }

    public int getEntregas() { return entregas; }
    public void incEntregas() { this.entregas++; }

    // 🔹 Métodos nuevos para el grafo
    public int getUbicacionActual() { return ubicacionActual; }
    public void setUbicacionActual(int ubicacionActual) { this.ubicacionActual = ubicacionActual; }
}

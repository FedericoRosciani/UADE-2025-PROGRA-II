package main.java.org.uade.structure.definition;

public interface SetADT {
    void add(int value); // Agrega un elemento al conjunto (si no está ya presente)
    void remove(int value); // Elimina un elemento del conjunto (si está presente)
    boolean exist(int value); // Verifica si un elemento está en el conjunto
    boolean isEmpty(); // Comprueba si el conjunto está vacío
    int choose(); // Retorna un elemento arbitrario del conjunto (sin eliminarlo)
}

package main.java.org.uade.structure.definition;

public interface PriorityQueueADT {
    int getElement(); //Retorna el elemento con mayor prioridad
    int getPriority(); //Retorna la prioridad del elemento con mayor prioridad
    void add(int value, int priority); //Agrega el elemento en la cola con su prioridad en el orden correcto por prioridad
    void remove(); //Elimina el elemento con mayor prioridad
    boolean isEmpty(); //Comprueba si la cola está vacía o no
}

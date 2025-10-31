package main.java.org.uade.structure.definition;

public interface LinkedListADT {
    void add (int value); //Agrega el elemento
    void insert (int index, int value); //Agrega el elemento a un índice
    void remove (int index); //Elimina el elemento de un índice
    int get(int index); //Retorna el elemento de un índice
    int size(); // Retorna tamaño de la lista
    boolean isEmpty(); //Comprueba si la lista está vacía o no
}

package main.java.org.uade.structure.implementation;
import main.java.org.uade.exception.IndexBoundsException;
import main.java.org.uade.structure.definition.LinkedListADT;

public class DynamicLinkedListADT implements LinkedListADT{
    private static class Node {
        int value;
        Node next;
        Node(int v) { value = v; }
    }

    private Node head;   // primer nodo
    private int size;    // cantidad de elementos

    @Override
    public void add(int value) {
        Node n = new Node(value);        // creo un nodo nuevo
        if (head == null) {              // si la lista está vacía
            head = n;                    // el nuevo nodo es el primero
        } else {
            Node p = head;               // arranco desde el inicio
            while (p.next != null)       // avanzo hasta el último nodo
                p = p.next;
            p.next = n;                  // engancho el nuevo al final
        }
        size++;
    }

    @Override
    public void insert(int index, int value) {
        if (index < 0 || index > size) throw new IndexBoundsException("índice fuera de rango"); // validar rango válido
        Node n = new Node(value);            // 1) crear el nodo nuevo
        if (index == 0) {                    // 2) caso: insertar al inicio
            n.next = head;                   // 3) nuevo apunta al viejo primero
            head = n;                        // 4) ahora el nuevo es el primero
        } else {
            Node p = head;                   // 5) arrancamos desde el principio
            for (int i = 0; i < index - 1; i++) // 6) avanzamos hasta el "anterior"
                p = p.next;
            n.next = p.next;                 // 7) nuevo apunta al "siguiente" del anterior
            p.next = n;                      // 8) anterior ahora apunta al nuevo
        }
        size++;                              // 9) aumenta el tamaño lógico
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size)    // validar rango válido
            throw new IndexBoundsException("índice fuera de rango");
        if (index == 0) {                  // caso especial: eliminar el primero
            head = head.next;              // salto al siguiente (borrando el primero)
        } else {
            Node p = head;                 // recorro desde el inicio
            for (int i = 0; i < index - 1; i++)
                p = p.next;                // avanzo hasta el nodo anterior
            p.next = p.next.next;          // hago que el anterior salte al siguiente
        }
        size--;                            // disminuyo el tamaño
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= size)    // validar rango válido
            throw new IndexBoundsException("índice fuera de rango");
        Node p = head;                     // arranco desde el inicio
        for (int i = 0; i < index; i++)
            p = p.next;                    // avanzo hasta el nodo en esa posición
        return p.value;                    // devuelvo el valor
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }
}
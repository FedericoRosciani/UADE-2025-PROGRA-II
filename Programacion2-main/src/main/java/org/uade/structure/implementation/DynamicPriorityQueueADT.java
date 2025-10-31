package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.PriorityQueueADT;

public class DynamicPriorityQueueADT implements PriorityQueueADT {
    private static class Node {
        int value;
        int priority;
        DynamicPriorityQueueADT.Node next;
        Node(int v, int p) { value = v; priority = p;}
    }
    private DynamicPriorityQueueADT.Node head;   // primer nodo
    private int size;    // cantidad de elementos

    @Override
    public int getElement() { //Retorna el elemento con mayor prioridad
        if (head == null) throw new IllegalStateException("La cola de prioridad está vacía");
        return head.value;
    }

    @Override
    public int getPriority() {
        if (head == null) throw new IllegalStateException("La cola de prioridad está vacía");
        return head.priority;
    }

    @Override
    public void add(int value, int priority) {
        //Agrega el elemento en la cola con su prioridad en el orden correcto por prioridad
        DynamicPriorityQueueADT.Node newNode = new DynamicPriorityQueueADT.Node(value, priority);
        if (head == null || priority > head.priority) {
            newNode.next = head;
            head = newNode;
        } else {
            DynamicPriorityQueueADT.Node current = head;
            while (current.next != null && current.next.priority >= priority) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;

    }

    @Override
    public void remove() {
        if (head == null) throw new IllegalStateException("La cola de prioridad está vacía");
        head = head.next;
        size--;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}

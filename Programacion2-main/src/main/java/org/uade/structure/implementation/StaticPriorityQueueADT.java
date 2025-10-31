package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.PriorityQueueADT;

public class StaticPriorityQueueADT implements PriorityQueueADT {
    private static class Node {
        int value;
        int priority;
        Node(int value, int priority) {
            this.value = value;
            this.priority = priority;
        }
    }
    private final int CAPACITY = 16;
    private final Node[] data = new Node[CAPACITY];
    private int size = 0;

    @Override
    public void remove() {
        if (isEmpty()) throw new IllegalStateException("La cola de prioridad está vacía");
        int highestPriorityIndex = 0;
        for (int i = 1; i < size; i++) {
            if (data[i].priority > data[highestPriorityIndex].priority) {
                highestPriorityIndex = i;
            }
        }
        // Shift elements to fill the gap
        for (int i = highestPriorityIndex; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null; // Clear the last element
        size--;

    }

    @Override
    public int getElement() {
        if (isEmpty()) throw new IllegalStateException("La cola de prioridad está vacía");
        int highestPriorityIndex = 0;
        for (int i = 1; i < size; i++) {
            if (data[i].priority > data[highestPriorityIndex].priority) {
                highestPriorityIndex = i;
            }
        }
        return data[highestPriorityIndex].value;
    }

    @Override
    public int getPriority() {
        if (isEmpty()) throw new IllegalStateException("La cola de prioridad está vacía");
        int highestPriorityIndex = 0;
        for (int i = 1; i < size; i++) {
            if (data[i].priority > data[highestPriorityIndex].priority) {
                highestPriorityIndex = i;
            }
        }
        return data[highestPriorityIndex].priority;
    }

    @Override
    public void add(int value, int priority) { //add element in the correct order based on priority
        if (size == CAPACITY) throw new IllegalStateException("La cola de prioridad está llena");
        data[size++] = new Node(value, priority);
    }

    @Override
    public boolean isEmpty() { // return true if the queue is empty
        return size == 0;
    }
}

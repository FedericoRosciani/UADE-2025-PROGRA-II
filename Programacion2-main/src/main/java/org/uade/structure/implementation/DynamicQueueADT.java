package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.LinkedListADT;
import main.java.org.uade.structure.definition.QueueADT;

import java.util.NoSuchElementException;

public class DynamicQueueADT implements QueueADT {
    private final LinkedListADT queue = new DynamicLinkedListADT();

    @Override
    public int getElement() {
        if (queue.isEmpty()) throw new NoSuchElementException("Cola vacía");
        return queue.get(0);
    }

    @Override
    public void add(int value) {
        queue.add(value);
    }

    @Override
    public void remove() {
        if (queue.isEmpty()) throw new NoSuchElementException("Cola vacía");
        queue.remove(0);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

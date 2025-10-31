package main.java.org.uade.structure.implementation;

import main.java.org.uade.exception.IsEmptyException;
import main.java.org.uade.structure.definition.ColaRecargadaTDA;
import main.java.org.uade.structure.definition.LinkedListADT;
import main.java.org.uade.structure.definition.SetADT;

import java.util.NoSuchElementException;

public class ColaRecargadaTDAimp implements ColaRecargadaTDA {
    private final LinkedListADT queue = new StaticLinkedListADT();
    @Override
    public SetADT pares() {
        return null;
    }

    @Override
    public int maximo() {
        if (queue.isEmpty()) throw new NoSuchElementException("Cola vacía");
        int max = queue.get(0);
        for (int i = 1; i < queue.size(); i++) {
            if (queue.get(i) > max) {
                max = queue.get(i);
            }
        }
        return max;
    }

    @Override
    public double promedio() {
        double suma = 0;
        for (int i = 0; i < queue.size(); i++) {
            suma += queue.get(i);
        }
        return suma / queue.size();
    }

    @Override
    public void multiplicar(int factor) {
        for (int i = 0; i < queue.size(); i++) {
            queue.insert(i, queue.get(i)*factor);
            queue.remove(i+1);
        }
    }

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

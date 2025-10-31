package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.LinkedListADT;
import main.java.org.uade.structure.definition.StackADT;

import java.util.NoSuchElementException;

public class DynamicStackADT implements StackADT {
    private final LinkedListADT stack = new DynamicLinkedListADT();

    @Override
    public int getElement() {
        if (stack.isEmpty()) throw new NoSuchElementException("Stack vacío");
        return stack.get(stack.size() - 1);
    }

    @Override
    public void add(int value) {
        stack.add(value);
    }

    @Override
    public void remove() {
        if (stack.isEmpty()) throw new NoSuchElementException("Stack vacío");
        stack.remove(stack.size() - 1);

    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}

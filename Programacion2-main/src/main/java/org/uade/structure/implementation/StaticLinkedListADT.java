package main.java.org.uade.structure.implementation;
import main.java.org.uade.exception.IndexBoundsException;
import main.java.org.uade.structure.definition.LinkedListADT;

public class StaticLinkedListADT implements LinkedListADT {
    private final int CAPACITY = 16;
    private final int[] data = new int[CAPACITY];
    private int size = 0;

    @Override
    public void add(int value) {
        if (size == CAPACITY) throw new IllegalStateException("lista llena");
        data[size] = value;
        size++;
    }

    @Override
    public void insert(int index, int value) {
        if (index < 0 || index > size) throw new IndexBoundsException("índice fuera de rango");
        if (size == CAPACITY) throw new IllegalStateException("lista llena");
        // desplazar elementos a la derecha
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexBoundsException("índice fuera de rango");
        // desplazar elementos a la izquierda
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexBoundsException("índice fuera de rango");
        return data[index];
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }else{
            return false;
        }
    }
    //   public boolean isEmpty() { return size == 0; }
}



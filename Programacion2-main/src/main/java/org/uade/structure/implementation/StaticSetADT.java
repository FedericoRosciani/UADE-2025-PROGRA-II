package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.SetADT;

import java.util.concurrent.ThreadLocalRandom;

import java.util.concurrent.ThreadLocalRandom;

public class StaticSetADT implements SetADT {
    private final StaticLinkedListADT list = new StaticLinkedListADT();

    @Override
    public void add(int value) {
        if (!exist(value)) {
            list.add(value); // inserta al final
        }
    }

    @Override
    public void remove(int value) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == value) {
                list.remove(i); // compacta a la izquierda
                return;
            }
        }
        // si no está, no hace nada
    }

    @Override
    public boolean exist(int value) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == value) return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int choose() {
        if (list.isEmpty()) {
            throw new IllegalStateException("El conjunto está vacío");
        }
        int idx = ThreadLocalRandom.current().nextInt(0, list.size());
        return list.get(idx);
    }
}


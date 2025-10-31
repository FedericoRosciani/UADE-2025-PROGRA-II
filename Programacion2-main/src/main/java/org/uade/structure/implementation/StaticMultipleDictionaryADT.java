package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.MultipleDictionaryADT;
import main.java.org.uade.structure.definition.SetADT;

public class StaticMultipleDictionaryADT implements MultipleDictionaryADT {
    //implementacion ESTATICA con tamaño fijo, de un diccionario multiple usando listas enlazadas

    private final int MAX_SIZE = 100; // Tamaño máximo del diccionario
    private Node[] table;
    private int size;
    public StaticMultipleDictionaryADT() {
        this.table = new Node[MAX_SIZE];
        this.size = 0;
    }
    private class Node {
        int key;
        int value;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    @Override
    public void add(int key, int value) {
        if (size >= MAX_SIZE) {
            throw new RuntimeException("Dictionary is full");
        }
        Node newNode = new Node(key, value);
        int index = Math.abs(Integer.hashCode(key)) % MAX_SIZE;
        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node current = table[index];
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode; // Agrega el nuevo nodo al final de la lista
        }
        size++;
    }

    @Override
    public void remove(int key) {
        int index = Math.abs(Integer.hashCode(key)) % MAX_SIZE;
        if (table[index] == null) return; // Diccionario vacío en ese índice
        while (table[index] != null && table[index].key == key) {
            table[index] = table[index].next; // Elimina nodos cabeza con la clave dada
            size--;
        }
        Node current = table[index];
        while (current != null && current.next != null) {
            if (current.next.key == key) {
                current.next = current.next.next; // Elimina el nodo con la clave dada
                size--;
            } else {
                current = current.next;
            }
        }

    }

    @Override
    public int[] get(int key) {
        int[] values = new int[size];
        int count = 0;
        int index = Math.abs(Integer.hashCode(key)) % MAX_SIZE;
        Node current = table[index];
        while (current != null) {
            if (current.key == key) {
                values[count++] = current.value;
            }
            current = current.next;
        }
        if (count == 0) {
            throw new RuntimeException("Key not found");
        }
        int[] result = new int[count];
        System.arraycopy(values, 0, result, 0, count);
        return result;
    }

    @Override
    public SetADT getKeys() {
        // Retorna un conjunto con todas las claves únicas en el diccionario
        SetADT keys = new StaticSetADT();
        for (int i = 0; i < MAX_SIZE; i++) {
            Node current = table[i];
            while (current != null) {
                if (!keys.exist(current.key)) {
                    keys.add(current.key);
                }
                current = current.next;
            }
        }
        return keys;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void remove(int key, int value) {
        int index = Math.abs(Integer.hashCode(key)) % MAX_SIZE;
        if (table[index] == null) return; // Diccionario vacío en ese índice
        while (table[index] != null && table[index].key == key && table[index].value == value) {
            table[index] = table[index].next; // Elimina nodos cabeza con la clave y valor dados
            size--;
        }
        Node current = table[index];
        while (current != null && current.next != null) {
            if (current.next.key == key && current.next.value == value) {
                current.next = current.next.next; // Elimina el nodo con la clave y valor dados
                size--;
            } else {
                current = current.next;
            }
        }
    }
}

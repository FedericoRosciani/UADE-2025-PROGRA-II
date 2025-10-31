package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.SetADT;
import main.java.org.uade.structure.definition.SimpleDictionaryADT;

public class StaticSimpleDictionaryADT implements SimpleDictionaryADT {
    //implementacion estatica de un diccionario simple usando listas enlazadas
    private final int MAX_SIZE = 100; // Tamaño máximo del diccionario
    private Node[] table;
    private int size;
    public StaticSimpleDictionaryADT() {
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
                if (current.key == key) {
                    current.value = value; // Actualiza el valor si la clave ya existe
                    return;
                }
                current = current.next;
            }
            if (current.key == key) {
                current.value = value; // Actualiza el valor si la clave ya existe
            } else {
                current.next = newNode; // Agrega el nuevo nodo al final de la lista
            }
        }
        size++;

    }

    @Override
    public void remove(int key) {
        int index = Math.abs(Integer.hashCode(key)) % MAX_SIZE;
        if (table[index] == null) return; // Diccionario vacío en ese índice
        if (table[index].key == key) {
            table[index] = table[index].next; // Elimina el nodo cabeza
            size--;
            return;
        }
        Node current = table[index];
        while (current != null && current.next != null) {
            if (current.next.key == key) {
                current.next = current.next.next; // Elimina el nodo con la clave dada
                size--;
                return;
            } else {
                current = current.next;
            }
        }

    }

    @Override
    public int get(int key) {
        int index = Math.abs(Integer.hashCode(key)) % MAX_SIZE;
        Node current = table[index];
        while (current != null) {
            if (current.key == key) {
                return current.value; // Retorna el valor si la clave existe
            }
            current = current.next;
        }
        throw new RuntimeException("Key not found"); // Lanza una excepción si la clave no existe
    }

    @Override
    public SetADT getKeys() {
        SetADT keys = new StaticSetADT();
        for (int i = 0; i < MAX_SIZE; i++) {
            Node current = table[i];
            while (current != null) {
                keys.add(current.key);
                current = current.next;
            }
        }
        return keys;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}

package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.MultipleDictionaryADT;
import main.java.org.uade.structure.definition.SetADT;

public class DynamicMultipleDictionaryADT implements MultipleDictionaryADT {
    //implementacion dinamica de un diccionario multiple usando listas enlazadas
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
    private Node head;
    private int size;
    public DynamicMultipleDictionaryADT() {
        this.head = null;
        this.size = 0;
    }
    @Override
    public void add(int key, int value) {
        Node newNode = new Node(key, value);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode; // Agrega el nuevo nodo al final de la lista
        }
        size++;
    }

    @Override
    public void remove(int key) {
        if (head == null) return; // Diccionario vacío
        while (head != null && head.key == key) {
            head = head.next; // Elimina nodos cabeza con la clave dada
            size--;
        }
        Node current = head;
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
        // retorna todos los valores asociados a la clave dada
        int[] values = new int[size];
        int count = 0;
        Node current = head;
        while (current != null) {
            if (current.key == key) {
                values[count++] = current.value;
            }
            current = current.next;
        }
        int[] result = new int[count];
        System.arraycopy(values, 0, result, 0, count);
        return result;
    }

    @Override
    public SetADT getKeys() {
        // retorna un conjunto con todas las claves del diccionario
        SetADT keys = new DynamicSetADT();
        Node current = head;
        while (current != null) {
            if (!keys.exist(current.key)) {
                keys.add(current.key);
            }
            current = current.next;
        }
        return keys;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void remove(int key, int value) {
        if (head == null) return; // Diccionario vacío
        while (head != null && head.key == key && head.value == value) {
            head = head.next; // Elimina nodos cabeza con la clave y valor dados
            size--;
        }
        Node current = head;
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
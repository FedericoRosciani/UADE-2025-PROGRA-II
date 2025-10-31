package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.SetADT;
import main.java.org.uade.structure.definition.SimpleDictionaryADT;

public class DynamicSimpleDictionaryADT implements SimpleDictionaryADT {
    //implementacion dinamica de un diccionario simple usando listas enlazadas
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
    public DynamicSimpleDictionaryADT() {
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
        if (head == null) return; // Diccionario vacío
        if (head.key == key) {
            head = head.next; // Elimina el nodo cabeza
            size--;
            return;
        }
    }
    @Override
    public int get(int key) {
        Node current = head;
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
        SetADT keys = new DynamicSetADT();
        Node current = head;
        while (current != null) {
            keys.add(current.key);
            current = current.next;
        }
        return keys;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

}
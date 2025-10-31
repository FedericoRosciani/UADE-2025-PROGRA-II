package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.GraphADT;
import main.java.org.uade.structure.definition.SetADT;

import java.nio.charset.Charset;

public class DynamicGraphADT implements GraphADT {
    // implementacion dinamica de un grafo usando listas de adyacencia usar metodos de GraphADT
    private class Node {
        int vertex;
        Node next;

        Node(int vertex) {
            this.vertex = vertex;
            this.next = null;
        }

    }
    private class AdjacencyList {
        Node head;

        AdjacencyList() {
            this.head = null;
        }
    }
    private AdjacencyList[] adjacencyLists;
    private int numVertices;
    public DynamicGraphADT(int initialCapacity) {
        this.adjacencyLists = new AdjacencyList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            adjacencyLists[i] = new AdjacencyList();
        }
        this.numVertices = 0;
    }
    //implementar los metodos de GraphADT
    @Override
    public SetADT getVertxs() {
        SetADT verticesSet = new DynamicSetADT();
        for (int i = 0; i < adjacencyLists.length; i++) {
            if (adjacencyLists[i].head != null) {
                verticesSet.add(i);
            }
        }
        return verticesSet;
    }

    @Override
    public void addVertx(int vertex) {
        for (int i = 0; i < adjacencyLists.length; i++) {
            if (adjacencyLists[i].head == null) {
                adjacencyLists[i].head = new Node(vertex);
                numVertices++;
                return;
            }
        }
    }

    @Override
    public void removeVertx(int vertex) {
        for (int i = 0; i < adjacencyLists.length; i++) {
            if (adjacencyLists[i].head != null && adjacencyLists[i].head.vertex == vertex) {
                adjacencyLists[i].head = null;
                numVertices--;
                return;
            }
        }
    }

    @Override
    public void addEdge(int vertxOne, int vertxTwo, int weight) {
        for (int i = 0; i < adjacencyLists.length; i++) {
            if (adjacencyLists[i].head != null && adjacencyLists[i].head.vertex == vertxOne) {
                Node newNode = new Node(vertxTwo);
                newNode.next = adjacencyLists[i].head.next;
                adjacencyLists[i].head.next = newNode;
                return;
            }
        }
    }

    @Override
    public void removeEdge(int vertxOne, int vertxTwo) {
        for (int i = 0; i < adjacencyLists.length; i++) {
            if (adjacencyLists[i].head != null && adjacencyLists[i].head.vertex == vertxOne) {
                Node current = adjacencyLists[i].head;
                Node prev = null;
                while (current != null) {
                    if (current.vertex == vertxTwo) {
                        if (prev == null) {
                            adjacencyLists[i].head = current.next;
                        } else {
                            prev.next = current.next;
                        }
                        return;
                    }
                    prev = current;
                    current = current.next;
                }
            }
        }
    }

    @Override
    public boolean existsEdge(int vertxOne, int vertxTwo) {
        for (int i = 0; i < adjacencyLists.length; i++) {
            if (adjacencyLists[i].head != null && adjacencyLists[i].head.vertex == vertxOne) {
                Node current = adjacencyLists[i].head;
                while (current != null) {
                    if (current.vertex == vertxTwo) {
                        return true;
                    }
                    current = current.next;
                }
            }
        }
        return false;
    }

    @Override
    public int edgeWeight(int vertxOne, int vertxTwo) {
        // en esta implementacion no se guarda el peso de la arista, por lo que se retorna 1 si existe la arista
        if (existsEdge(vertxOne, vertxTwo)) {
            return 1;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }
}
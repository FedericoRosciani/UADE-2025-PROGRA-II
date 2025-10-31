package main.java.org.uade.structure.implementation;

import main.java.org.uade.structure.definition.GraphADT;
import main.java.org.uade.structure.definition.SetADT;

public class StaticGraphADT implements GraphADT {
    //implementacion estatica de un grafo usando una matriz de adyacencia
    private final int MAX_VERTICES = 100;
    private int[][] adjacencyMatrix;
    private int vertexCount;
    public StaticGraphADT() {
        this.adjacencyMatrix = new int[MAX_VERTICES][MAX_VERTICES];
        this.vertexCount = 0;
    }

    @Override
    public SetADT getVertxs() {
        SetADT vertices = new StaticSetADT();
        for (int i = 0; i < vertexCount; i++) {
            vertices.add(i);
        }
        return vertices;
    }

    @Override
    public void addVertx(int vertex) {
        if (vertexCount >= MAX_VERTICES) {
            throw new RuntimeException("Maximum number of vertices reached");
        }
        vertexCount++;
    }

    @Override
    public void removeVertx(int vertex) {
        // Removing a vertex in an adjacency matrix requires shifting rows and columns
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("Vertex does not exist");
        }
        for (int i = vertex; i < vertexCount - 1; i++) {
            for (int j = 0; j < vertexCount; j++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i + 1][j];
            }
        }
        for (int j = vertex; j < vertexCount - 1; j++) {
            for (int i = 0; i < vertexCount - 1; i++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i][j + 1];
            }
        }
        vertexCount--;
    }

    @Override
    public void addEdge(int vertxOne, int vertxTwo, int weight) {
        if (vertxOne < 0 || vertxOne >= vertexCount || vertxTwo < 0 || vertxTwo >= vertexCount) {
            throw new IllegalArgumentException("One or both vertices do not exist");
        }
        adjacencyMatrix[vertxOne][vertxTwo] = weight;
        adjacencyMatrix[vertxTwo][vertxOne] = weight; // Assuming undirected graph
    }

    @Override
    public void removeEdge(int vertxOne, int vertxTwo) {
        if (vertxOne < 0 || vertxOne >= vertexCount || vertxTwo < 0 || vertxTwo >= vertexCount) {
            throw new IllegalArgumentException("One or both vertices do not exist");
        }
        adjacencyMatrix[vertxOne][vertxTwo] = 0;
        adjacencyMatrix[vertxTwo][vertxOne] = 0; // Assuming undirected graph
    }

    @Override
    public boolean existsEdge(int vertxOne, int vertxTwo) {
        if (vertxOne < 0 || vertxOne >= vertexCount || vertxTwo < 0 || vertxTwo >= vertexCount) {
            throw new IllegalArgumentException("One or both vertices do not exist");
        }
        return adjacencyMatrix[vertxOne][vertxTwo] != 0;
    }

    @Override
    public int edgeWeight(int vertxOne, int vertxTwo) {
        if (vertxOne < 0 || vertxOne >= vertexCount || vertxTwo < 0 || vertxTwo >= vertexCount) {
            throw new IllegalArgumentException("One or both vertices do not exist");
        }
        return adjacencyMatrix[vertxOne][vertxTwo];
    }

    @Override
    public boolean isEmpty() {
        return vertexCount == 0;
    }
}

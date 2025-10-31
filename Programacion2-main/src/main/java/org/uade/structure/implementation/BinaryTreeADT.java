package main.java.org.uade.structure.implementation;

public class BinaryTreeADT implements main.java.org.uade.structure.definition.BinaryTreeADT {
    private class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public BinaryTreeADT() {
        this.root = null;
    }

    @Override
    public int getRoot() {
        if (root == null) throw new IllegalStateException("Tree is empty");
        return root.value;
    }

    @Override
    public BinaryTreeADT getLeft() {
        if (root == null || root.left == null) throw new IllegalStateException("Left subtree is empty");
        BinaryTreeADT leftTree = new BinaryTreeADT();
        leftTree.root = root.left;
        return leftTree;
    }

    @Override
    public BinaryTreeADT getRight() {
        if (root == null || root.right == null) throw new IllegalStateException("Right subtree is empty");
        BinaryTreeADT rightTree = new BinaryTreeADT();
        rightTree.root = root.right;
        return rightTree;
    }

    @Override
    public void add(int value) {
        root = addRecursive(root, value);
    }

    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        }
        return current;
    }

    @Override
    public void remove(int value) {
        root = removeRecursive(root, value);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    private Node removeRecursive(Node current, int value) {
        if (current == null) {
            return null;
        }
        if (value == current.value) {
            // Node to delete found
            if (current.left == null && current.right == null) {
                return null; // No children
            }
            if (current.right == null) {
                return current.left; // One child
            }
            if (current.left == null) {
                return current.right; // One child
            }
            // Two children
            int smallestValue = findSmallestValue(current.right);
            current.value = smallestValue;
            current.right = removeRecursive(current.right, smallestValue);
            return current;
        }
        if (value < current.value) {
            current.left = removeRecursive(current.left, value);
            return current;
        }
        current.right = removeRecursive(current.right, value);
        return current;
    }

    private int findSmallestValue(Node root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }
}


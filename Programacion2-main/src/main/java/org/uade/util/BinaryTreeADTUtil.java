package main.java.org.uade.util;

public class BinaryTreeADTUtil {
    // create methods for in-order, post-order pre-order and level-order traversals

    public static void inOrderTraversal(main.java.org.uade.structure.definition.BinaryTreeADT tree) {
        if (tree == null || tree.isEmpty()) {
            return;
        }
        inOrderTraversal(tree.getLeft());
        System.out.print(tree.getRoot() + " ");
        inOrderTraversal(tree.getRight());
    }

    public static void preOrderTraversal(main.java.org.uade.structure.definition.BinaryTreeADT tree) {
        if (tree == null || tree.isEmpty()) {
            return;
        }
        System.out.print(tree.getRoot() + " ");
        preOrderTraversal(tree.getLeft());
        preOrderTraversal(tree.getRight());
    }

    public static void postOrderTraversal(main.java.org.uade.structure.definition.BinaryTreeADT tree) {
        if (tree == null || tree.isEmpty()) {
            return;
        }
        postOrderTraversal(tree.getLeft());
        postOrderTraversal(tree.getRight());
        System.out.print(tree.getRoot() + " ");
    }

    public static void levelOrderTraversal(main.java.org.uade.structure.definition.BinaryTreeADT tree) {
        if (tree == null || tree.isEmpty()) {
            return;
        }
        java.util.Queue<main.java.org.uade.structure.definition.BinaryTreeADT> queue = new java.util.LinkedList<>();
        queue.add(tree);
        while (!queue.isEmpty()) {
            main.java.org.uade.structure.definition.BinaryTreeADT current = queue.poll();
            System.out.print(current.getRoot() + " ");
            if (!current.getLeft().isEmpty()) {
                queue.add(current.getLeft());
            }
            if (!current.getRight().isEmpty()) {
                queue.add(current.getRight());
            }
        }
    }

    //add method to print the tree in a structured way (like a sideways tree)
    public static void printTree(main.java.org.uade.structure.definition.BinaryTreeADT tree, String prefix, boolean isLeft) {
        if (tree == null || tree.isEmpty()) {
            return;
        }
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + tree.getRoot());
        printTree(tree.getLeft(), prefix + (isLeft ? "│   " : "    "), true);
        printTree(tree.getRight(), prefix + (isLeft ? "│   " : "    "), false);
    }
}

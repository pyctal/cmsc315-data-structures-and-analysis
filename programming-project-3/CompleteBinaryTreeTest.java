
/**
 * Unit tests for CompleteBinaryTree utilizing Java assert statements. Run with
 * the -ea flag to enable assertions.
 */

import java.util.ArrayList;

public class CompleteBinaryTreeTest {

    public static void main(String[] args) {
        System.out.println("Starting CompleteBinaryTree Unit Tests...");
        int passed = 0;
        int total = 5;

        try {
            testTreeConstruction();
            passed++;
            testIsMaxHeap();
            passed++;
            testIsBinarySearchTree();
            passed++;
            testEmptyAndSingleNode();
            passed++;
            testInorderList();
            passed++;

            System.out.println("\n" + passed + "/" + total + " tests passed!");
        } catch (AssertionError | Exception e) {
            System.out.println("\nTEST SUITE FAILED!");
            System.out.println(e.getMessage());
        }
    }

    public static void testTreeConstruction() {
        System.out.println("Testing tree construction and exceptions...");

        // Test Valid Input
        try {
            CompleteBinaryTree tree = new CompleteBinaryTree("90 70 50 20 40");
            assert tree != null : "Expected tree to be instantiated successfully.";
        } catch (InvalidTreeException e) {
            assert false : "Valid input should not throw an exception.";
        }

        // Test Invalid Input
        try {
            CompleteBinaryTree badTree = new CompleteBinaryTree("90 70 abc 20");
            assert false : "Expected InvalidTreeException to be thrown for non-integer tokens.";
        } catch (InvalidTreeException e) {
            assert e.getMessage().equals("Node value must be an integer.") : "Incorrect exception message.";
        }
    }

    public static void testIsMaxHeap() {
        System.out.println("Testing isMaxHeap...");

        try {
            // Valid Max-Heap
            CompleteBinaryTree heapTree = new CompleteBinaryTree("90 70 50 20 40 10 25");
            assert heapTree.isMaxHeap() : "Expected true: Tree is a valid max-heap.";

            // Invalid Max-Heap (20 is greater than parent 17)
            CompleteBinaryTree invalidHeap = new CompleteBinaryTree("40 35 17 22 19 20");
            assert !invalidHeap.isMaxHeap() : "Expected false: 20 is greater than its parent 17.";
        } catch (InvalidTreeException e) {
            assert false : "Unexpected exception during setup.";
        }
    }

    public static void testIsBinarySearchTree() {
        System.out.println("Testing isBinarySearchTree...");

        try {
            // Valid BST
            CompleteBinaryTree bstTree = new CompleteBinaryTree("10 5 15 2 7 12");
            assert bstTree.isBinarySearchTree() : "Expected true: Tree is a valid BST.";

            // Invalid BST (15 is in right subtree of 20, but it is less than 20)
            CompleteBinaryTree invalidBst = new CompleteBinaryTree("20 18 15 10");
            assert !invalidBst.isBinarySearchTree() : "Expected false: 15 violates BST rules against root 20.";
        } catch (InvalidTreeException e) {
            assert false : "Unexpected exception during setup.";
        }
    }

    public static void testEmptyAndSingleNode() {
        System.out.println("Testing edge cases (empty & single node)...");

        try {
            // Empty Tree
            CompleteBinaryTree emptyTree = new CompleteBinaryTree("");
            assert emptyTree.isMaxHeap() : "Expected true: Empty tree is technically a max-heap.";
            assert emptyTree.isBinarySearchTree() : "Expected true: Empty tree is technically a BST.";

            // Single Node Tree
            CompleteBinaryTree singleNode = new CompleteBinaryTree("6");
            assert singleNode.isMaxHeap() : "Expected true: Single node is a max-heap.";
            assert singleNode.isBinarySearchTree() : "Expected true: Single node is a BST.";
        } catch (InvalidTreeException e) {
            assert false : "Unexpected exception during setup.";
        }
    }

    public static void testInorderList() {
        System.out.println("Testing inorderList...");

        try {
            CompleteBinaryTree bstTree = new CompleteBinaryTree("10 5 15 2 7 12");
            ArrayList<Integer> result = bstTree.inorderList();

            assert result.size() == 6 : "Expected 6 elements in the inorder list.";
            assert result.get(0) == 2 : "Expected 2 at index 0.";
            assert result.get(1) == 5 : "Expected 5 at index 1.";
            assert result.get(2) == 7 : "Expected 7 at index 2.";
            assert result.get(3) == 10 : "Expected 10 at index 3.";
            assert result.get(4) == 12 : "Expected 12 at index 4.";
            assert result.get(5) == 15 : "Expected 15 at index 5.";

        } catch (InvalidTreeException e) {
            assert false : "Unexpected exception during setup.";
        }
    }
}

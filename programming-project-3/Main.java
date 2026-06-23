
/**
 * Priyam Mohanty
 * Programming Project 3: Binary Trees
 * 23rd June, 2026
 *
 * The driver class to test the CompleteBinaryTree implementation. It prompts
 * the user for a tree sequence, displays the preorder traversal, and outputs
 * checks for max-heap, BST, and an inorder listing.
 */
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a binary tree: ");
        String treeString = input.nextLine();

        try {
            CompleteBinaryTree tree = new CompleteBinaryTree(treeString);

            // Task 2: Display structurally indented Preorder traversal
            tree.preorder();

            // Task 3: Check if the tree is a max-heap
            System.out.println("Is a max-heap: " + tree.isMaxHeap());

            // Task 4: Check if the tree is a binary search tree
            System.out.println("Is a binary search tree: " + tree.isBinarySearchTree());

            // Task 5: Return and print an in-order list of values
            System.out.println("Inorder List: " + tree.inorderList());

        } catch (InvalidTreeException e) {
            System.out.println(e.getMessage());
        } finally {
            input.close();
        }
    }
}

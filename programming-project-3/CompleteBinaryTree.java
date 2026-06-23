
/**
 * Priyam Mohanty
 * Programming Project 3: Binary Trees
 * 23rd June, 2026
 *
 * Represents a complete binary tree. Includes functionality to build the tree
 * from an array or string, traverse it (preorder, inorder), and check if it
 * satisfies max-heap or binary search tree properties.
 */
import java.util.ArrayList;

public class CompleteBinaryTree {

    protected TreeNode root;

    /**
     * A static nested class representing a node in the binary tree. Contains an
     * integer value and references to left and right children.
     */
    public static class TreeNode {

        protected Integer value;
        protected TreeNode left;
        protected TreeNode right;

        /**
         * Constructs a TreeNode with a given integer value.
         *
         * @param value the value to store in the node
         */
        public TreeNode(Integer value) {
            this.value = value;
        }
    }

    /**
     * Constructs a CompleteBinaryTree from an array of Integer values that
     * represent a complete binary tree in level-order.
     *
     * If the input array is not null and contains elements, it initializes the
     * root of the tree by calling the recursive method `makeNode`, starting
     * from index 0.
     *
     * @param values an array of Integer values representing the binary tree in
     * level-order
     * @throws InvalidTreeException if the array contains a null element where a
     * node is expected
     */
    public CompleteBinaryTree(Integer[] values) throws InvalidTreeException {
        if (values != null && values.length > 0) {
            root = makeNode(values, 0);
        }
    }

    /**
     * Constructs a CompleteBinaryTree from a whitespace-separated string of
     * integers representing the tree in level-order.
     *
     * The string is parsed into integer tokens and used to recursively build
     * the tree starting from index 0 via {@code makeNode}.
     *
     * If the input is null or contains only whitespace, the tree is considered
     * empty ({@code root} is null). If any token is not a valid integer, an
     * {@code InvalidTreeException} is thrown.
     *
     * @param levelOrderValues the level-order representation of the tree as a
     * string
     * @throws InvalidTreeException if any token is not a valid integer
     */
    public CompleteBinaryTree(String levelOrderValues) throws InvalidTreeException {
        if (levelOrderValues == null || levelOrderValues.trim().isEmpty()) {
            root = null;
            return;
        }

        String[] tokens = levelOrderValues.trim().split("\\s+");
        Integer[] values = new Integer[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            try {
                values[i] = Integer.parseInt(tokens[i]);
            } catch (NumberFormatException e) {
                throw new InvalidTreeException("Node value must be an integer.");
            }
        }

        root = makeNode(values, 0);
    }

    /**
     * Recursively constructs a complete binary tree from an array. The array is
     * assumed to represent a complete binary tree in level-order traversal.
     *
     * For each index `i` in the array: - The element at index `i` represents
     * the node. - The left child of the node is at index `2*i + 1`. - The right
     * child of the node is at index `2*i + 2`.
     *
     * This method constructs the tree in a level-by-level manner.
     *
     * @param values array of integer values representing the tree in
     * level-order
     * @param index current index in the array that corresponds to the current
     * node
     * @return TreeNode at the current index, with left and right children
     * recursively constructed
     * @throws InvalidTreeException if a node value is null or invalid
     */
    protected TreeNode makeNode(Integer[] values, int index) throws InvalidTreeException {
        if (index >= values.length) {
            return null;
        }
        if (values[index] == null) {
            throw new InvalidTreeException("Node element must not be null");
        }

        TreeNode node = new TreeNode(values[index]);
        node.left = makeNode(values, 2 * index + 1);
        node.right = makeNode(values, 2 * index + 2);

        return node;
    }

    /**
     * Performs a preorder traversal of the tree.
     */
    public void preorder() {
        System.out.println("Preorder: ");
        preorder(root, 0);
    }

    /**
     * Recursive helper method for preorder traversal. Prints each node's value
     * with indentation based on its depth level to visually represent the tree
     * structure.
     *
     * @param root the current subtree root
     * @param level the indentation level, increases with depth in the tree
     */
    private void preorder(TreeNode root, int level) {
        if (root == null) {
            return;
        }

        // Print 4 spaces per level
        for (int i = 0; i < level * 4; i++) {
            System.out.print(" ");
        }

        System.out.println(root.value);
        preorder(root.left, level + 1);
        preorder(root.right, level + 1);
    }

    /**
     * Checks whether the binary tree satisfies the max-heap property.
     *
     * @return true if the tree is a max-heap, false otherwise
     */
    public boolean isMaxHeap() {
        return isMaxHeap(root);
    }

    private boolean isMaxHeap(TreeNode node) {
        if (node == null) {
            return true;
        }

        if (node.left != null && node.value < node.left.value) {
            return false;
        }
        if (node.right != null && node.value < node.right.value) {
            return false;
        }

        return isMaxHeap(node.left) && isMaxHeap(node.right);
    }

    /**
     * Checks whether the tree is a valid binary search tree (BST). A BST is
     * valid if, for every node: - All nodes in the left subtree are strictly
     * less than the node's value. - All nodes in the right subtree are strictly
     * greater than the node's value.
     *
     * @return true if the tree satisfies BST properties, false otherwise
     */
    public boolean isBinarySearchTree() {
        return isBinarySearchTree(root, null, null);
    }

    /**
     * Recursive helper method to check BST property using range limits. At each
     * node, ensures: - Node's value is strictly greater than the min bound. -
     * Node's value is strictly less than the max bound. Recursively checks left
     * and right subtrees with updated bounds.
     *
     * @param node current node in the tree
     * @param min lower bound (exclusive) for the node's value
     * @param max upper bound (exclusive) for the node's value
     * @return true if the subtree rooted at the current node is a valid BST,
     * false otherwise
     */
    private boolean isBinarySearchTree(TreeNode node, Integer min, Integer max) {
        if (node == null) {
            return true;
        }

        if (min != null && node.value <= min) {
            return false;
        }
        if (max != null && node.value >= max) {
            return false;
        }

        return isBinarySearchTree(node.left, min, node.value)
                && isBinarySearchTree(node.right, node.value, max);
    }

    /**
     * Returns an ArrayList containing the values of the tree nodes in in- order
     * traversal, visiting nodes in the following order: - Left subtree -
     * Current node - Right subtree
     *
     * @return an ArrayList containing the values of the nodes in in-order
     * traversal
     */
    public ArrayList<Integer> inorderList() {
        ArrayList<Integer> list = new ArrayList<>();
        inorderList(root, list);
        return list;
    }

    private void inorderList(TreeNode node, ArrayList<Integer> list) {
        if (node == null) {
            return;
        }

        inorderList(node.left, list);
        list.add(node.value);
        inorderList(node.right, list);
    }
}

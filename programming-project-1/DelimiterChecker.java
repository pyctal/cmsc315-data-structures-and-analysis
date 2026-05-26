
/**
 * Priyam Mohanty
 * Programming Project 1
 * 26th May, 2026
 *
 * DelimiterChecker contains the main logic to verify matching delimiters in a
 * Java file. It continually prompts the user for a valid file name, reads
 * through it using JavaCodeReader, and uses a stack to ensure {}, [], and ()
 * are perfectly balanced.
 */
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class DelimiterChecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JavaCodeReader reader = null;

        // Loop until a valid file name is entered
        while (reader == null) {
            System.out.print("Enter the Java source file name to check: ");
            String fileName = scanner.nextLine();

            try {
                reader = new JavaCodeReader(fileName);
                System.out.println(
                        "File loaded successfully. Analyzing delimiters...\n");
            } catch (FileNotFoundException e) {
                System.out.println("Error: File not found. Please try again.");
            }
        }

        Stack<Character> stack = new Stack<>();
        boolean mismatchFound = false;
        char ch;

        // Continuously read characters until EOF ('\0') or a mismatch occurs
        while ((ch = reader.getNextValidChar()) != '\0') {
            // Push left delimiters to the stack
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } // Check right delimiters against the stack
            else if (ch == ')' || ch == '}' || ch == ']') {

                // Case 1: Stack is empty (we found a closing delimiter with no opening one)
                if (stack.isEmpty()) {
                    System.out.println(
                            "Mismatch error: Found unexpected closing '" + ch
                            + "' at " + reader.getPosition());
                    mismatchFound = true;
                    break;
                }

                // Case 2: Pop and check if they match
                char top = stack.pop();
                if (!isMatchingPair(top, ch)) {
                    System.out.println("Mismatch error: Found '" + ch
                            + "' but expected closing delimiter for '" + top
                            + "' at " + reader.getPosition());
                    mismatchFound = true;
                    break;
                }
            }
        }

        // Final check: if no explicit mismatch was triggered, verify the stack is empty
        if (!mismatchFound) {
            if (stack.isEmpty()) {
                System.out.println("Success: All delimiters match correctly!");
            } else {
                // Leftover delimiters that were never closed
                char unmatched = stack.pop();
                System.out.println(
                        "Mismatch error: Reached end of file with unclosed '"
                        + unmatched + "'.");
            }
        }

        scanner.close();
    }

    /**
     * Helper method to determine if a left and right delimiter are a valid
     * pair.
     */
    private static boolean isMatchingPair(char left, char right) {
        return (left == '(' && right == ')')
                || (left == '{' && right == '}')
                || (left == '[' && right == ']');
    }
}


/**
 * Priyam Mohanty
 * Programming Project 1
 * 26th May, 2026
 *
 * JavaCodeReader encapsulates a Java source file and reads it character by
 * character. It intentionally filters out characters that are part of line
 * comments, block comments, string literals, or character literals, ensuring
 * only structural code is returned. It also accurately tracks the line number
 * and character position.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JavaCodeReader {

    private final BufferedReader reader;
    private int lineNumber = 1;
    private int charNumber = 0;

    // A 1-char buffer used to "push back" characters when peeking ahead 
    // (e.g., checking if a '/' is a division sign or a comment)
    private Integer pushedBackChar = null;

    /**
     * Constructor that accepts the file name to be tested.
     *
     * @param fileName The path to the Java source file.
     * @throws FileNotFoundException if the file does not exist.
     */
    public JavaCodeReader(String fileName) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(fileName));
    }

    /**
     * Returns the next structural character in the file, skipping all comments
     * and literals.
     *
     * @return The next valid character, or '\0' (null character) if the end of
     * file is reached.
     */
    public char getNextValidChar() {
        try {
            int ch;
            while ((ch = getRawChar()) != -1) {
                if (ch == '"') {
                    skipStringLiteral();
                } else if (ch == '\'') {
                    skipCharLiteral();
                } else if (ch == '/') {
                    int next = getRawChar();
                    if (next == '/') {
                        skipLineComment();
                    } else if (next == '*') {
                        skipBlockComment();
                    } else {
                        // It was just a division sign. Save the 'next' char for later and return '/'
                        pushedBackChar = next;
                        return (char) ch;
                    }
                } else {
                    return (char) ch;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return '\0'; // Return null character to indicate EOF
    }

    /**
     * Reads the raw character and updates position coordinates.
     */
    private int getRawChar() throws IOException {
        // If we pushed a character back previously, return it without updating coordinates again
        if (pushedBackChar != null) {
            int ch = pushedBackChar;
            pushedBackChar = null;
            return ch;
        }

        int ch = reader.read();
        if (ch != -1) {
            if (ch == '\n') {
                lineNumber++;
                charNumber = 0;
            } else {
                charNumber++;
            }
        }
        return ch;
    }

    private void skipStringLiteral() throws IOException {
        int ch;
        while ((ch = getRawChar()) != -1) {
            if (ch == '\\') {
                getRawChar(); // Skip escaped characters (e.g., \")
            } else if (ch == '"') {
                break; // End of string
            }
        }
    }

    private void skipCharLiteral() throws IOException {
        int ch;
        while ((ch = getRawChar()) != -1) {
            if (ch == '\\') {
                getRawChar(); // Skip escaped characters (e.g., \')
            } else if (ch == '\'') {
                break; // End of character
            }
        }
    }

    private void skipLineComment() throws IOException {
        int ch;
        while ((ch = getRawChar()) != -1) {
            if (ch == '\n') {
                break; // End of line comment
            }
        }
    }

    private void skipBlockComment() throws IOException {
        int ch;
        while ((ch = getRawChar()) != -1) {
            if (ch == '*') {
                int next = getRawChar();
                if (next == '/') {
                    break; // End of block comment
                } else {
                    pushedBackChar = next; // Push back in case it's **/, etc.
                }
            }
        }
    }

    /**
     * @return A string containing the current line number and character number.
     */
    public String getPosition() {
        return "Line " + lineNumber + ", Character " + charNumber;
    }
}

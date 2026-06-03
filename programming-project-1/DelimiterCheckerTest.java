
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Test Suite for DelimiterChecker class. MUST BE RUN WITH THE '-ea' FLAG: java
 * -ea DelimiterCheckerTest
 */
public class DelimiterCheckerTest {

    private static File tempFile;
    private static final InputStream originalSystemIn = System.in;
    private static final PrintStream originalSystemOut = System.out;
    private static ByteArrayOutputStream outContent;

    public static void main(String[] args) {
        System.out.println("Starting Test Suite...\n");
        int passed = 0;
        int total = 8;

        try {
            testValidCode();
            passed++;
            testMissingClosingDelimiter();
            passed++;
            testMismatchedClosingDelimiter();
            passed++;
            testExtraClosingDelimiter();
            passed++;
            testUnbalancedDelimitersInComments();
            passed++;
            testUnbalancedDelimitersInLiterals();
            passed++;
            testDivisionSymbolsLookahead();
            passed++;
            testInvalidFileNameRetry();
            passed++;

            System.out.println("\n" + passed + "/" + total + " tests passed!");
        } catch (AssertionError | Exception e) {
            // Catch failed assertions, restore standard output, and print the failure
            originalSystemOut.println("TEST SUITE FAILED!");
            originalSystemOut.println(e.getMessage());
        } finally {
            tearDown(); // Failsafe cleanup
        }
    }

    // Create temp file for DelimiterChecker and hijack console output.
    private static void setUp() throws IOException {
        tempFile = File.createTempFile("TestCase", ".java");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent)); // Hijack console output
    }

    // Delete temp file used for testing and restore system input/output.
    private static void tearDown() {
        System.setIn(originalSystemIn);   // Restore normal keyboard
        System.setOut(originalSystemOut); // Restore normal console
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete(); // Clean up temp file
        }
    }

    // Helps write to the temp file and execute the program.
    private static void runTestLogic(String codeContent) throws Exception {
        setUp();

        // Write the test code to our temporary file
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(codeContent);
        }

        // Simulate typing the filename and hitting Enter
        String simulatedInput = tempFile.getAbsolutePath()
                + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Run the main program
        DelimiterChecker.main(new String[]{});
    }

    // --- TEST CASES ---
    public static void testValidCode() throws Exception {
        runTestLogic("""
        class Main {
            public static void main(String[] args) {
                System.out.println("Hello");
            }
        }
        """);

        String actualOutput = outContent.toString();
        tearDown(); // Restore console so we can print or throw errors normally

        assert actualOutput.contains("Success: All delimiters match correctly!") :
                "Test 1 Failed: testValidCode\nOutput was:\n" + actualOutput;

        System.out.println("Test 1: testValidCode - PASSED");
    }

    public static void testMissingClosingDelimiter() throws Exception {
        runTestLogic("""
        class Main {
            public static void main(String[] args) {
        """);

        String actualOutput = outContent.toString();
        tearDown();

        assert actualOutput.contains(
                "Mismatch error: Reached end of file with unclosed '{'") :
                "Test 2 Failed: testMissingClosingDelimiter\nOutput was:\n"
                + actualOutput;

        System.out.println("Test 2: testMissingClosingDelimiter - PASSED");
    }

    public static void testMismatchedClosingDelimiter() throws Exception {
        runTestLogic("""
        class Main {
            public static void main(String[] args) ]
        }
        """);

        String actualOutput = outContent.toString();
        tearDown();

        assert actualOutput.contains("Mismatch error: Found ']' but expected "
                + "closing delimiter for '{' at Line 2, Character 44") :
                "Test 3 Failed: testMismatchedClosingDelimiter\nOutput was:\n"
                + actualOutput;

        System.out.println("Test 3: testMismatchedClosingDelimiter - PASSED");
    }

    public static void testExtraClosingDelimiter() throws Exception {
        runTestLogic("""
        class Main {
            public static void main(String[] args) {
            }
        } )
        """);

        String actualOutput = outContent.toString();
        tearDown();

        assert actualOutput.contains("Mismatch error: Found unexpected closing "
                + "')' at Line 4, Character 3") :
                "Test Failed: Test 4: testExtraClosingDelimiter\nOutput was:\n"
                + actualOutput;

        System.out.println("Test 4: testExtraClosingDelimiter - PASSED");
    }

    public static void testUnbalancedDelimitersInComments() throws Exception {
        runTestLogic("""
        class Main {
            // { [ (
            /* { [ ( */
        }
        """);

        String actualOutput = outContent.toString();
        tearDown();

        assert actualOutput.contains("Success: All delimiters match correctly!") :
                "Test Failed: Test 5: testUnbalancedDelimitersInComments\n"
                + "Output was:\n" + actualOutput;

        System.out.println("Test 5: testUnbalancedDelimitersInComments - PASSED");
    }

    public static void testUnbalancedDelimitersInLiterals() throws Exception {
        runTestLogic("""
        class Main {
            String s = "{";
            char c = '[';
        }
        """);

        String actualOutput = outContent.toString();
        tearDown();

        assert actualOutput.contains("Success: All delimiters match correctly!") :
                "Test 6 Failed: testUnbalancedDelimitersInLiterals\nOutput was:\n"
                + actualOutput;

        System.out.println("Test 6: testUnbalancedDelimitersInLiterals - PASSED");
    }

    public static void testDivisionSymbolsLookahead() throws Exception {
        runTestLogic("""
        class Main {
            int a = 10 / 2;
            // {
        }
        """);

        String actualOutput = outContent.toString();
        tearDown();

        assert actualOutput.contains("Success: All delimiters match correctly!") :
                "Test 7 Failed: testDivisionSymbolsLookahead\nOutput was:\n"
                + actualOutput;

        System.out.println("Test 7: testDivisionSymbolsLookahead - PASSED");
    }

    public static void testInvalidFileNameRetry() throws Exception {
        // Custom setup because this test requires feeding two lines of keyboard input
        setUp();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("class Main { }"); // Valid code for the second attempt
        }

        // Input: "bad_file.java" [ENTER] "good_file.java" [ENTER]
        String simulatedUserInput = "fake_file_does_not_exist.java\n"
                + tempFile.getAbsolutePath() + "\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        DelimiterChecker.main(new String[]{});

        String actualOutput = outContent.toString();
        tearDown();

        // Assert that both the error message AND the final success message were printed
        assert actualOutput.contains("Error: File not found. Please try again.")
                && actualOutput.contains("Success: All delimiters match correctly!") :
                "Test 8 Failed: testInvalidFileNameRetry\nOutput was:\n"
                + actualOutput;

        System.out.println("Test 8: testInvalidFileNameRetry - PASSED");
    }
}

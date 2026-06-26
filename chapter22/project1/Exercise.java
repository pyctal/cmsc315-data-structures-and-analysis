
import java.util.Scanner;

public class Exercise {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String inputString = input.nextLine();

        if (inputString.isEmpty()) {
            System.out.println("Maximum consecutive increasingly ordered substring is ");
            return;
        }

        int maxSubstringStart = 0;
        int maxSubstringLen = 1;
        int currSubstringStart = 0;
        int currSubstringLen = 1;

        for (int i = 1; i < inputString.length(); i++) {
            // If character is greater than previous, extend current substring
            if (inputString.charAt(i) > inputString.charAt(i - 1)) {
                currSubstringLen++;
            } else {
                // Sequence broken. Check to ensure longer substring is kept in records
                if (currSubstringLen > maxSubstringLen) {
                    maxSubstringStart = currSubstringStart;
                    maxSubstringLen = currSubstringLen;
                }

                // Reset for new substring
                currSubstringStart = i;
                currSubstringLen = 1;
            }
        }

        // Final check for substrings which reached the end of input string
        if (currSubstringLen > maxSubstringLen) {
            maxSubstringStart = currSubstringStart;
            maxSubstringLen = currSubstringLen;
        }

        System.out.println("Maximum consecutive increasingly ordered substring is "
                + inputString.substring(maxSubstringStart, maxSubstringStart + maxSubstringLen));
    }
}

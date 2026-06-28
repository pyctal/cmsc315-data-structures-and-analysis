
import java.util.Scanner;

public class Exercise {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a series of numbers ending with 0: ");

        int maxSequenceNum = 0;
        int maxSequenceStart = 0;
        int maxSequenceCount = 0;

        int currSequenceNum = 0;
        int currSequenceStart = 0;
        int currSequenceCount = 0;

        int index = 0;
        while (input.hasNextInt()) {
            int num = input.nextInt();

            // Check if end of input
            if (num == 0) {
                break;
            }

            if (num == currSequenceNum) {
                // Continue current sequence
                currSequenceCount++;
            } else {
                // Sequence broken, check if current sequence longer than max
                if (currSequenceCount > maxSequenceCount) {
                    maxSequenceNum = currSequenceNum;
                    maxSequenceStart = currSequenceStart;
                    maxSequenceCount = currSequenceCount;
                }
                // Reset sequence tracks for new number
                currSequenceNum = num;
                currSequenceStart = index;
                currSequenceCount = 1;
            }

            index++;
        }

        // Final check for num sequence which reached the end of the input string
        if (currSequenceCount > maxSequenceCount) {
            maxSequenceNum = currSequenceNum;
            maxSequenceStart = currSequenceStart;
            maxSequenceCount = currSequenceCount;
        }

        if (maxSequenceCount > 0) {
            System.out.println("The longest same number sequence starts at index " + maxSequenceStart + " with "
                    + maxSequenceCount + " values of " + maxSequenceNum);
        } else {
            System.out.println("No valid sequence was entered");
        }

        input.close();
    }
}

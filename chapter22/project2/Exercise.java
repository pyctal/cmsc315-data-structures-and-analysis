
import java.util.Scanner;

public class Exercise {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a string s1: ");
        String str1 = input.nextLine();

        System.out.print("Enter a string s2: ");
        String str2 = input.nextLine();

        if (str1.isEmpty() || str2.isEmpty()) {
            System.out.println("s2 is not a substring of s1");
            return;
        }

        int matchingIndex = -1;
        boolean isMatching = false;
        for (int i = 0; i <= str1.length() - str2.length(); i++) {
            isMatching = true;

            // Check if str2 matches str1 staring at index i
            for (int j = 0; j < str2.length(); j++) {
                if (str1.charAt(i + j) != str2.charAt(j)) {
                    isMatching = false;
                    break;
                }
            }

            // Break loop if match found
            if (isMatching) {
                matchingIndex = i;
                break;
            }
        }

        if (isMatching) {
            System.out.println("Matched at index " + matchingIndex);
        } else {
            System.out.println("s2 is not a substring of s1");
        }

        input.close();
    }
}

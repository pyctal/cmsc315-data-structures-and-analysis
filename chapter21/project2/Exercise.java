
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Exercise {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HashMap<Character, Integer> letterCounts = new HashMap<>();

        System.out.print("Enter a string: ");
        String inputLine = input.nextLine().toLowerCase();

        for (int i = 0; i < inputLine.length(); i++) {
            char ch = inputLine.charAt(i);
            if (Character.isLetter(ch)) {
                letterCounts.put(ch, letterCounts.getOrDefault(ch, 0) + 1);
            }
        }

        ArrayList<HashMap.Entry<Character, Integer>> entryList = new ArrayList<>(letterCounts.entrySet());
        entryList.sort(HashMap.Entry.<Character, Integer>comparingByValue().thenComparing(HashMap.Entry.comparingByKey()));

        for (HashMap.Entry<Character, Integer> entry : entryList) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        input.close();
    }
}

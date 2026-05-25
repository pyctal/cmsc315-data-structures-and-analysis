
import java.util.Scanner;
import java.util.TreeSet;

public class Exercise {

    public static void main(String[] args) {
        TreeSet<String> set1 = new TreeSet<>();
        TreeSet<String> set2 = new TreeSet<>();
        Scanner input = new Scanner(System.in);

        System.out.print("Enter strings for the first set: ");
        String inputLine1 = input.nextLine();

        System.out.print("Enter strings for the second set: ");
        String inputLine2 = input.nextLine();

        populateSet(set1, inputLine1);
        populateSet(set2, inputLine2);

        TreeSet<String> unionSet = (TreeSet<String>) set1.clone();
        unionSet.addAll(set2);
        System.out.println("The union of the two sets is " + unionSet);

        TreeSet<String> differenceSet = (TreeSet<String>) set1.clone();
        differenceSet.removeAll(set2);
        System.out.println("The difference of the two sets is " + differenceSet);

        TreeSet<String> intersectionSet = (TreeSet<String>) set1.clone();
        System.out.println("The difference of the two sets is " + intersectionSet);

        input.close();
    }

    private static void populateSet(TreeSet<String> set, String inputLine) {
        Scanner lineScanner = new Scanner(inputLine);

        while (lineScanner.hasNext()) {
            set.add(lineScanner.next());
        }

        lineScanner.close();
    }
}

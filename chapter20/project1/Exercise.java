
import java.util.PriorityQueue;
import java.util.Scanner;

public class Exercise {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PriorityQueue<Integer> queue1 = new PriorityQueue<>();
        PriorityQueue<Integer> queue2 = new PriorityQueue<>();

        System.out.print("Enter integers for priority queue 1: ");
        String inputLine1 = input.nextLine();

        System.out.print("Enter integers for priority queue 2: ");
        String inputLine2 = input.nextLine();

        populateQueue(queue1, inputLine1);
        populateQueue(queue2, inputLine2);

        System.out.println("The union of the two priority queues is");
        PriorityQueue<Integer> union = new PriorityQueue<>(queue1);
        union.addAll(queue2);
        printAndEmptyQueue(union);

        System.out.println("The difference of the two priority queues is");
        PriorityQueue<Integer> difference = new PriorityQueue<>(queue1);
        difference.removeAll(queue2);
        printAndEmptyQueue(difference);

        System.out.println("The intersection of the two priority queues is");
        PriorityQueue<Integer> intersection = new PriorityQueue<>(queue1);
        intersection.retainAll(queue2);
        printAndEmptyQueue(intersection);

        input.close();
    }

    private static void populateQueue(PriorityQueue<Integer> queue, String inputLine) {
        Scanner lineScanner = new Scanner(inputLine);

        while (lineScanner.hasNextInt()) {
            queue.offer(lineScanner.nextInt());
        }

        lineScanner.close();
    }

    private static void printAndEmptyQueue(PriorityQueue<Integer> queue) {
        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + " ");
        }
        System.out.println();
    }
}

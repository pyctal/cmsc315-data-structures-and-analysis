
import java.util.*;

/**
 * Test Suite for NLPUtilityTest class. MUST BE RUN WITH THE '-ea' FLAG: java
 * -ea NLPUtilityTest
 */
public class NLPUtilityTest {

    public static void main(String[] args) {
        System.out.println("Starting NLPUtility Unit Tests...");
        int passed = 0;
        int total = 5;

        try {
            testSplitTextIntoTokens();
            passed++;
            testCountFilteredWords();
            passed++;
            testSortByValueDescending();
            passed++;
            testGetSentiment();
            passed++;
            testGetWordsWithMaxFrequency();
            passed++;

            System.out.println("\n" + passed + "/" + total + " tests passed!");
        } catch (AssertionError | Exception e) {
            System.out.println("TEST SUITE FAILED!");
            System.out.println(e.getMessage());
        }
    }

    public static void testSplitTextIntoTokens() {
        System.out.println("Testing splitTextIntoTokens...");
        String input = "WOW!?! That.?# is REALLY (really) amazing! ";
        String[] tokens = NLPUtility.splitTextIntoTokens(input);

        assert tokens.length == 6 : "Expected 6 tokens, got " + tokens.length;
        assert tokens[0].equals("WOW") : "Expected WOW";
        assert tokens[1].equals("That") : "Expected That";
        assert tokens[5].equals("amazing") : "Expected amazing";
    }

    public static void testCountFilteredWords() {
        System.out.println("Testing countFilteredWords...");
        String[] words = {"i", "love", "a", "good", "BOOK", "and", "LOVE", "sad", "Book", "book"};
        Set<String> stopWords
                = new HashSet<>(Arrays.asList("is", "in", "at", "of", "and", "a", "to", "it", "the", "or", "was", "so"));

        TreeMap<String, Integer> result = NLPUtility.countFilteredWords(words, stopWords);

        // Sorting verification (TreeMap strictly guarantees alphabetical order of keys)
        assert result.size() == 5 : "Expected 5 unique valid words";
        assert result.get("book") == 3 : "Expected book count 3";
        assert result.get("love") == 2 : "Expected love count 2";

        List<String> keys = new ArrayList<>(result.keySet());
        assert keys.get(0).equals("book") : "Expected 'book' as first key (alphabetical)";
        assert keys.get(4).equals("sad") : "Expected 'sad' as last key";
    }

    public static void testSortByValueDescending() {
        System.out.println("Testing sortByValueDescending...");
        Map<String, Integer> map = new TreeMap<>();
        map.put("book", 3);
        map.put("good", 1);
        map.put("i", 1);
        map.put("love", 2);
        map.put("sad", 1);

        LinkedHashMap<String, Integer> result = NLPUtility.sortByValueDescending(map);

        // Verify sorting by value descending
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(result.entrySet());
        assert entries.get(0).getKey().equals("book") : "Expected book (3) first";
        assert entries.get(1).getKey().equals("love") : "Expected love (2) second";

        // Verify tie preservation (good, i, sad should maintain original alphabetical order from the TreeMap insertion)
        assert entries.get(2).getKey().equals("good") : "Expected good (1) third due to tie-break";
        assert entries.get(3).getKey().equals("i") : "Expected i (1) fourth";
    }

    public static void testGetSentiment() {
        System.out.println("Testing getSentiment...");
        Map<String, Integer> wordMap = new LinkedHashMap<>();
        wordMap.put("book", 3);
        wordMap.put("love", 2);
        wordMap.put("good", 1);
        wordMap.put("i", 1);
        wordMap.put("sad", 1);

        Set<String> positive = new HashSet<>(Arrays.asList("good", "love"));
        Set<String> negative = new HashSet<>(Arrays.asList("sad", "bad"));

        String sentiment = NLPUtility.getSentiment(wordMap, positive, negative);
        assert sentiment.equals("Positive: 3, Negative: 1") : "Incorrect sentiment calculation: " + sentiment;
    }

    public static void testGetWordsWithMaxFrequency() {
        System.out.println("Testing getWordsWithMaxFrequency...");
        Map<String, Integer> wordMap = new LinkedHashMap<>();
        wordMap.put("good", 1);
        wordMap.put("i", 1);
        wordMap.put("love", 3);
        wordMap.put("book", 3);
        wordMap.put("sad", 1);

        Map<String, Object> result = NLPUtility.getWordsWithMaxFrequency(wordMap);

        assert (Integer) result.get("frequency") == 3 : "Expected max frequency 3";

        @SuppressWarnings("unchecked")
        List<String> maxWords = (List<String>) result.get("words");
        assert maxWords.size() == 2 : "Expected 2 words with max frequency";
        assert maxWords.get(0).equals("book") : "Expected 'book' first (alphabetical sorting)";
        assert maxWords.get(1).equals("love") : "Expected 'love' second";
    }
}

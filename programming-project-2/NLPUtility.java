
/**
 * Priyam Mohanty
 * Programming Project 2: Word Frequency & Sentiment Analysis Program
 * 9th June, 2026
 *
 * Contains static computational methods responsible for executing raw string
 * formatting, filtering out unneeded elements, transforming maps, analyzing
 * dictionary metrics, and sorting collections across the natural language
 * processing pipeline.
 */
import java.util.*;

public class NLPUtility {

    /**
     * Splits the given text into word tokens using one or more whitespace or
     * punctuation characters as delimiters.
     *
     * @param text the input string to be tokenized
     * @return an array of word tokens, excluding punctuation and whitespace
     */
    public static String[] splitTextIntoTokens(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new String[0];
        }
        // Removes leading delimiters to prevent an empty string token at the start of the array
        String cleanedText = text.replaceAll("^[\\s\\p{P}]+", "");
        return cleanedText.split("[\\s\\p{P}]+");
    }

    /**
     * Counts the frequency of words in the given array, excluding those present
     * in the specified set of stop words. The comparison is case-insensitive,
     * and results are stored in a {@link TreeMap} sorted alphabetically by
     * word.
     *
     * @param words An array of tokenized words to analyze.
     * @param stopWords A set of words to exclude from the frequency count
     * (e.g., common stop words like "the", "and").
     * @return A {@link TreeMap} mapping each non-stop word to its frequency,
     * sorted alphabetically.
     */
    public static TreeMap<String, Integer> countFilteredWords(String[] words, Set<String> stopWords) {
        TreeMap<String, Integer> wordCountMap = new TreeMap<>();
        for (String word : words) {
            if (word.trim().isEmpty()) {
                continue;
            }

            String lowerCaseWord = word.toLowerCase();
            if (!stopWords.contains(lowerCaseWord)) {
                wordCountMap.put(lowerCaseWord, wordCountMap.getOrDefault(lowerCaseWord, 0) + 1);
            }
        }

        return wordCountMap;
    }

    /**
     * Sorts the entries of a map by their values in descending order. The
     * result is returned as a {@link LinkedHashMap} to preserve the order of
     * sorted entries.
     *
     * @param map A map containing keys and integer values to be sorted by
     * value.
     * @return A {@link LinkedHashMap} containing the same entries as the input
     * map, sorted in descending order by value.
     */
    public static LinkedHashMap<String, Integer> sortByValueDescending(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());

        // Sort descending by value (frequency)
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    /**
     * Performs sentiment analysis by scanning the word-frequency map. Adds up
     * the total frequency of all words found in the predefined positive and
     * negative word sets.
     *
     * @param wordMap A map of words and their frequencies.
     * @return A summary string in the format: "Positive: X, Negative: Y" where
     * X and Y are the total counts of positive and negative words.
     */
    public static String getSentiment(Map<String, Integer> wordMap, Set<String> positiveWords,
            Set<String> negativeWords) {
        int positiveCount = 0;
        int negativeCount = 0;

        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            String word = entry.getKey().toLowerCase();
            if (positiveWords.contains(word)) {
                positiveCount += entry.getValue();
            } else if (negativeWords.contains(word)) {
                negativeCount += entry.getValue();
            }
        }

        return "Positive: " + positiveCount + ", Negative: " + negativeCount;
    }

    /**
     * Finds the words with the highest frequency in the given map and returns a
     * map containing a sorted word list along with the maximum frequency value.
     *
     * @param wordMap A map of words and their corresponding frequencies.
     * @return A map containing: - "words": A list of words with the highest
     * frequency, sorted alphabetically. - "frequency": The highest frequency
     * value.
     */
    public static Map<String, Object> getWordsWithMaxFrequency(Map<String, Integer> wordMap) {
        Map<String, Object> resultMap = new HashMap<>();

        if (wordMap == null || wordMap.isEmpty()) {
            resultMap.put("words", new ArrayList<String>());
            resultMap.put("frequency", 0);
            return resultMap;
        }

        // Find the maximum frequency value in the map
        int maxFrequency = 0;
        for (int frequency : wordMap.values()) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
            }
        }

        // Collect all words that match the maximum frequency
        List<String> mostFrequentWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                mostFrequentWords.add(entry.getKey());
            }
        }

        Collections.sort(mostFrequentWords);

        resultMap.put("words", mostFrequentWords);
        resultMap.put("frequency", maxFrequency);

        return resultMap;
    }

}

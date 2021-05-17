package Q6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Anonymous (do not change)
 * <p>
 * Question 6:
 * <p>
 * Implement in Java a similar algorithm to that in Q5, i.e. given a
 * block of text your algorithm should be able to redact words from
 * a given set and outputs the result to a file called â€œresult.txtâ€�.
 * However, in this implementation of the algorithm all
 * redactable words will be proper nouns (i.e. a name used for an
 * individual person, place, or organisation, spelled with an initial
 * capital letter) and your algorithm should take into account that
 * the list of redactable words might not be complete. For example,
 * given the block of text:
 * It was in July, 1805, and the speaker was the well-known Anna
 * Pavlovna Scherer, maid of honor and favorite of the Empress
 * Marya Fedorovna. With these words she greeted Prince Vasili
 * Kuragin, a man of high rank and importance, who was the first
 * to arrive at her reception. Anna Pavlovna had had a cough for
 * some days. She was, as she said, suffering from la grippe;
 * grippe being then a new word in St. Petersburg, used only by
 * the elite.
 * <p>
 * and the redactable set of words
 * Anna Pavlovna Scherer, St. Petersburg, Marya Fedorovna
 * <p>
 * the output text should be
 * It was in ****, 1805, and the speaker was the well-known ****
 * ******** *******, maid of honor and favorite of the *******
 * ***** *********. With these words she greeted ****** ******
 * *******, a man of high rank and importance, who was the first
 * to arrive at her reception. **** ******** had had a cough for
 * some days. She was, as she said, suffering from la grippe;
 * grippe being then a new word in *** **********, used only by
 * the elite.
 * <p>
 * You should test your program using the example files provided.
 */

public class CWK2Q6 {

    public static void redactWords(String textFilename, String redactWordsFilename) {
        String fileContent = readFile(textFilename);
        String redactContent = readFile(redactWordsFilename);
        // Process redact content into a list of words
        Set<String> wordsToRedactFromFile = new HashSet<>(processContentToListOfWords(redactContent));

        String result = redactNouns(redact(fileContent, wordsToRedactFromFile));

        System.out.println(result);
    }

    public static String redact(String text, Set<String> explicitlyRedacted) {
        String redacted = text;
        for (String s : explicitlyRedacted) {
            redacted = redacted.replace(s, repeat("*", s.length()));
        }
        return redacted;
    }

    public static String redactNouns(String text) {
        String[] tokens = Pattern.compile("\\b").split(text);

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            // capitalized && not (start of a sentence)
            if (!token.isEmpty() && Character.isUpperCase(token.charAt(0)) && !
//                    (i == 0 || tokens[i - 1].endsWith("\r\n\r\n") || tokens[i - 1].trim().endsWith("."))) {
//                    (i == 0 || tokens[i - 1].matches("\\R\\R$") || tokens[i - 1].trim().endsWith("."))) {
                    (i == 0 || tokens[i - 1].matches("(\\R\\R|\\.\\s*)$"))) {
                tokens[i] = repeat("*", token.length());
            }
        }
        return String.join("", tokens);
    }

    private static String repeat(String symbol, int n) {
        StringBuilder result = new StringBuilder(symbol.length() * n);
        for (int i = 0; i < n; i++) {
            result.append(symbol);
        }
        return result.toString();
    }

    public static List<String> processContentToListOfWords(String redactContent) {
        List<String> wordsToRedact = new ArrayList<>();
        String[] phrases = redactContent.split("\\s*,\\s*");
        for (String phrase : phrases) {
            String[] words = phrase.split(" ");
            for (String word : words) {
                if (word.contains(".")) {
                    word = word.substring(0, word.length() - 1);
                }
                wordsToRedact.add(word);
            }
        }
        return wordsToRedact;
    }

    public static String readFile(String pathName) {
        File file = new File(pathName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.useDelimiter("\\Z");
        return scanner.next();
    }

    public static void main(String[] args) {
        // Keep this here as it was from the original download
//		String inputFile = "./debate.txt";
//		String redactFile = "./redact.txt";
//		redactWords(inputFile, redactFile);

//        String inputFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleFile.txt";
//        String redactFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleRedact.txt";

        String inputFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/warandpeace.txt";
        String redactFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/redact.txt";

        redactWords(inputFile, redactFile);
    }

}

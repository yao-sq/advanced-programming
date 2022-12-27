package Q6;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

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

        writeFile("./result.txt", result);
        System.out.println(result);
    }

    public static String redact(String text, Set<String> explicitlyRedacted) {
        String redacted = text;
        for (String s : explicitlyRedacted) {
            redacted = redacted.replace(s, repeat("*", s.length()));
        }
        return redacted;
    }

    private static final Pattern PATTERN_SENTENCE_END = Pattern.compile("(\\R\\R|\\.\\s*)['\"“”‘’„]?$");
    public static String redactNouns(String text) {
        String[] tokens = Pattern.compile("\\b").split(text);

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            // capitalized && not (start of a sentence)
            if (!token.isEmpty() && Character.isUpperCase(token.charAt(0)) && !
//                    (i == 0 || tokens[i - 1].endsWith("\r\n\r\n") || tokens[i - 1].trim().endsWith("."))) {
//                    (i == 0 || tokens[i - 1].matches("\\R\\R$") || tokens[i - 1].trim().endsWith("."))) {
//                    (i == 0 || tokens[i - 1].matches("(\\R\\R|\\.\\s*)$"))) {
                    (i == 0 || PATTERN_SENTENCE_END.matcher(tokens[i - 1]).find())) {
                if (token.chars().allMatch(Character::isUpperCase)) continue;
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
        return Arrays.stream(redactContent.split("\\s*[,\n]\\s*"))
                .flatMap(phrase -> Arrays.stream(phrase.split(" ")))
                .collect(toList());
    }

    public static String readFile(String pathName) {
        try (Scanner scanner = new Scanner(new File(pathName))) {
            return scanner.useDelimiter("\\Z").next();
        }
        catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void writeFile(String pathName, String contentToWrite)  {
        try (FileWriter writer = new FileWriter(pathName)) {
            writer.write(contentToWrite);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public static void main(String[] args) {
//		String inputFile = "./debate.txt";
//		String redactFile = "./redact.txt";
//		redactWords(inputFile, redactFile);

//        String inputFile = "./exampleFile.txt";
//        String redactFile = "./exampleRedact.txt";

        String inputFile = "./warandpeace.txt";
        String redactFile = "./redact.txt";

        redactWords(inputFile, redactFile);
    }

}

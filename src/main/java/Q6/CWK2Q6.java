package Q6;

import javax.xml.stream.events.Characters;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

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
        List<String> wordsToRedactFromFile = processContentToListOfWords(redactContent);

        List<String> wordsInText = processContentToListOfWords(fileContent);
        // Traverse the big block of text, word by word, and check if the word is in the words to redact?
        for (String wordInText : wordsInText) {
//            System.out.println(wordInText);
            if (wordsToRedactFromFile.contains(wordInText)) {
                fileContent = fileContent.replace( wordInText, toStars(wordInText));
            }


            //FIXME:  cases not working:  1. start of sentences; 2. St.
            if ( !wordInText.isEmpty() && Character.isUpperCase(wordInText.charAt(0))) {
                fileContent = fileContent.replace( wordInText, toStars(wordInText));
            }
        }
        System.out.println(fileContent);
    }

    public static String redact(String text, Set<String> explicitlyRedacted) {
        String redacted = text;
        for (String s : explicitlyRedacted) {
            redacted = redacted.replace(s, repeat("*", s.length()));
        }

        String[] tokens = Pattern.compile("\\b").split(redacted);
//        String[] tokens = Pattern.compile("(?<=\\s)(?=\\S)").split(text);

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            // is explicitely redacted OR (capitalized && not start of a sentence)
            if (//explicitlyRedacted.contains(token) ||
                    (!token.isEmpty() && Character.isUpperCase(token.charAt(0))
                    && !(i == 0 || tokens[i - 1].trim().endsWith(".")))) {
                tokens[i] = repeat("*", token.length());
            }
        }
        return String.join("", tokens);
    }

    private static String repeat(String text, int n) {
        StringBuilder result = new StringBuilder(text.length() * n);
        for (int i = 0; i < n; i++) {
            result.append(text);
        }
        return result.toString();
    }

    public static String toStars(String wordFrom) {
        String wordTo = "";
        int wordLength = wordFrom.length();
        for (int i = 0; i < wordLength; i++) {
                wordTo = wordTo.concat("*");
        }
        return wordTo;
    }

    public static List<String> processContentToListOfWords(String redactContent) {
        List<String> wordsToRedact = new ArrayList<>();
        String[] phrases = redactContent.split("\\s*,\\s*");
        for (String phrase : phrases) {
            String[] words = phrase.split(" ");
            for (String word : words) {
                if (word.contains(".")){
                    word = word.substring(0, word.length()-1);
                }
                wordsToRedact.add(word);
            }
        }
        return wordsToRedact;
    }


    public static void redactWords_backup(String textFilename, String redactWordsFilename) {
        String fileContent = readFile(textFilename);
        String redactContent = readFile(redactWordsFilename);

        String[] phrases = redactContent.split("\\s*,\\s*");

        String redacted = redactNouns(fileContent, asList(phrases));
        System.out.println(redacted);
    }

    public static String redactNouns(String text, List<String> phrases) {
        ArrayList<String> words = new ArrayList<>();
        for (String phrase : phrases) {
            for (String word : phrase.split(" ")) {
                words.add(word);
            }
        }
        String redacted = redactWords(text, words);
        // Processing other proper nouns
        redacted = redactOtherNouns(redacted);

        return redacted;
    }

    public static String redactWords(String text, List<String> words) {
        // Processing redact words recorded in the file
        return replaceWordsInFile(text, words);
    }

    public static String redactOtherNouns(String text) {
        List<String> otherProperNouns = getOtherProperNouns(text);
        return replaceWordsInFile(text, otherProperNouns);
    }

    public static List<String> getOtherProperNouns(String fileContent) {
        List<String> properNouns = new ArrayList<>();

        // Break file into sentences.
        String[] sentences = fileContent.split("\\. ");
        for (String sentence : sentences) {
            String[] words = sentence.split(" ");
            for (String word : words) {
                if (sentence.indexOf(word) != 0) {
                    if (Character.isUpperCase(word.charAt(0))) {
                        String processedWord = !word.contains(",") ? word : word.substring(0, word.length() - 1);
                        properNouns.add(processedWord);
                    }
                }
            }
        }
        return properNouns;
    }

    public static String replaceWordsInFile(String fileContent, List<String> phrases) {
        for (String phrase : phrases) {
            int phraseLength = phrase.length();
            if (fileContent.contains(phrase)) {
                String replacement = "";
                for (int i = 0; i < phraseLength; i++) {
                    if (phrase.charAt(i) == ' ') {
                        replacement = replacement.concat(" ");
                    } else {
                        replacement = replacement.concat("*");
                    }
                }
                fileContent = fileContent.replace(phrase, replacement);
            }
        }
        return fileContent;
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
        // keep this here as it was from the original download
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

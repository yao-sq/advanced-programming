package Q6;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;

/**
 *  @author Anonymous (do not change)
 *
 *  Question 6:
 *
 *  Implement in Java a similar algorithm to that in Q5, i.e. given a
 *  block of text your algorithm should be able to redact words from
 *  a given set and outputs the result to a file called â€œresult.txtâ€�.
 *  However, in this implementation of the algorithm all
 *  redactable words will be proper nouns (i.e. a name used for an
 *  individual person, place, or organisation, spelled with an initial
 *  capital letter) and your algorithm should take into account that
 *  the list of redactable words might not be complete. For example,
 *  given the block of text:
 *      It was in July, 1805, and the speaker was the well-known Anna
 *      Pavlovna Scherer, maid of honor and favorite of the Empress
 *      Marya Fedorovna. With these words she greeted Prince Vasili
 *      Kuragin, a man of high rank and importance, who was the first
 *      to arrive at her reception. Anna Pavlovna had had a cough for
 *      some days. She was, as she said, suffering from la grippe;
 *      grippe being then a new word in St. Petersburg, used only by
 *      the elite.
 *
 *  and the redactable set of words
 *      Anna Pavlovna Scherer, St. Petersburg, Marya Fedorovna
 *
 *  the output text should be
 *      It was in ****, 1805, and the speaker was the well-known ****
 *      ******** *******, maid of honor and favorite of the *******
 *      ***** *********. With these words she greeted ****** ******
 *      *******, a man of high rank and importance, who was the first
 *      to arrive at her reception. **** ******** had had a cough for
 *      some days. She was, as she said, suffering from la grippe;
 *      grippe being then a new word in *** **********, used only by
 *      the elite.
 *
 *  You should test your program using the example files provided.
*/

public class CWK2Q6 {

	public static void redactWords(String textFilename, String redactWordsFilename){
		String fileContent = readFile(textFilename);
		String redactNounsContent = readFile(redactWordsFilename);

		String[] redactNouns = redactNounsContent.split("\\s*,\\s*");

		String redacted = redactNouns(fileContent, asList(redactNouns));
		System.out.println(redacted);
	}

	public static String redactNouns(String text, List<String> redactNouns) {
		ArrayList<String> redactWords = new ArrayList<>();
		for (String noun : redactNouns) {
			for (String redactWord : noun.split(" ")) {
				redactWords.add(redactWord);
			}
		}
		String redacted = redactWords(text, redactWords);


		// Processing other proper nouns
		redacted = redactOtherNouns(redacted);

		return redacted;
	}

	public static String redactWords(String text, List<String> redactWords) {
		// Processing redact words recorded in the file
		return replaceWordsInFile(text, redactWords);
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
				if ( sentence.indexOf(word) !=0 ) {
					if ( Character.isUpperCase(word.charAt(0))) {
						String processedWord = !word.contains(",") ? word: word.substring(0, word.length()-1);
						properNouns.add(processedWord);
					}
				}
			}
		}
		return properNouns;  //FIXME: Petersberg
	}

	public static String replaceWordsInFile(String fileContent, List<String> entities) {
		for (String entity : entities) {
			int entityLength = entity.length();
			if (fileContent.contains(entity)) {
				String replacement = "";
				for (int i = 0; i < entityLength; i++) {
					if ( entity.charAt(i) == ' ') {
						replacement = replacement.concat(" ");
					}
					else {
						replacement = replacement.concat("*");
					}
				}
				fileContent = fileContent.replace(entity, replacement);
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
		String s = scanner.next();
		return s;
	}

	public static void main(String[] args) {
		// keep this here as it was from the original download
//		String inputFile = "./debate.txt";
//		String redactFile = "./redact.txt";
//		redactWords(inputFile, redactFile);

		String inputFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleFile.txt";
		String redactFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleRedact.txt";

		redactWords(inputFile, redactFile);
	}

}

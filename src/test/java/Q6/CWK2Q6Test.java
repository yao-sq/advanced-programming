package Q6;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class CWK2Q6Test {
//    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private PrintStream originalOut = System.out;
//
//    @BeforeEach
//    public void setUpStreams() {
//        System.setOut(new PrintStream(outContent));
//    }
//
//    @AfterEach
//    public void restoreStreams() {
//        System.setOut(originalOut);
//    }
//
//    @Test
//    public void outputCapture() {
//        System.out.print("hello");
//        assertThat(outContent.toString()).isEqualTo("hello");
//    }

    @Test
    void redactWords_forShortExample() {
        String inputFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleFile.txt";
        String redactFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleRedact.txt";
        String expected = "It was in ****, 1805, and the speaker was the well-known **** ******** *******, maid of honor and favorite of the ******* ***** *********. With these words she greeted ****** ****** *******, a man of high rank and importance, who was the first to arrive at her reception. **** ******** had had a cough for some days. She was, as she said, suffering from la grippe; grippe being then a new word in *** **********, used only by the elite.";

        Set<String> explicitlyRedacted = new HashSet<>(asList("Anna", "July", "Pavlovna", "Scherer", "Empress", "Marya", "Fedorovna", "Prince", "Vasili", "Kuragin", "Pavlovna", "St.", "Petersburg"));

        String result = CWK2Q6.redact(CWK2Q6.readFile(inputFile), explicitlyRedacted);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void redactWords_forLongExample() {
        String inputFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/warandpeace.txt";
        String redactFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/redact.txt";

        Set<String> explicitlyRedacted = new HashSet<>(asList("Ilya", "Rostov", "St.", "Petersburg", "Marya", "Fedorovna", "Moscow", "Anna", "Pavlovna", "Scherer", "Countess", "Bezukhova", "Andrew", "Bolkonski", "Boris", "Drubetskoy", "Vasili", "Kuragin", "Nicholas", "Rostov"));

        String result = CWK2Q6.redact(CWK2Q6.readFile(inputFile), explicitlyRedacted);
        System.out.println(result);

        //TODO: BOOK NINE is BOOK **** -- ok?
        //TODO: "But every time ...
        //TODO: I
        //TODO: sometimes CHAPTER is marked, sometimes it's not
        //TODO: new line after previous line stops with ."
    }


//    @Test
//    void redactWords_forShortExample() {
//        String inputFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleFile.txt";
//        String redactFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleRedact.txt";
//        String expected = "It was in ****, 1805, and the speaker was the well-known **** ******** *******, maid of honor and favorite of the ******* ***** *********. With these words she greeted ****** ****** *******, a man of high rank and importance, who was the first to arrive at her reception. **** ******** had had a cough for some days. She was, as she said, suffering from la grippe; grippe being then a new word in *** **********, used only by the elite.";
//
//        CWK2Q6.redactWords(inputFile, redactFile);
//        assertThat(outContent.toString().trim()).isEqualTo(expected);
//    }
//
//    @Test
//    void splitContentInto_words(){
//        List<String> expected = asList("Anna", "Pavlovna", "Scherer", "St.", "Petersburg", "Marya", "Fedorovna");
//
//        String redactContent = CWK2Q6.readFile("/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleRedact.txt");
//        List<String> actual = CWK2Q6.processContentToListOfWords(redactContent);
//
//        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
//    }
//
//    @Test
//    void wordsToRedact_isSplitInto_phrase() {
//        String redactWords = CWK2Q6.readFile("/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleRedact.txt");
//        String[] phrases = redactWords.split("\\s*,\\s*");
//        for (String phrase : phrases) {
//            System.out.println(phrase);
//        }
//    }
//
//    @Test
//    void properNouns() {
//        String fileContent = CWK2Q6.readFile("/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleFile.txt");
//        List<String> expected = asList("Anna", "July", "Pavlovna", "Scherer", "Empress", "Marya", "Fedorovna", "Prince", "Vasili", "Kuragin", "Pavlovna", "St", "Petersberg");
//        List<String> properNouns = CWK2Q6.getOtherProperNouns(fileContent);
//
//        Assertions.assertThat(properNouns).containsExactlyInAnyOrderElementsOf(expected);
//    }
//
//    @Test
//    void replace() {
//        String inputContent = "It was in July, 1805, and the speaker was the well-known Anna Pavlovna Scherer, maid of honor and favorite of the Empress Marya Fedorovna.";
//        String expectedContent = "It was in July, 1805, and the speaker was the well-known **** ******** Scherer, maid of honor and favorite of the Empress Marya Fedorovna.";
//
//        ArrayList<String> entities = new ArrayList<>(asList("Anna", "Pavlovna"));
//        String redactedContent = CWK2Q6.replaceWordsInFile(inputContent, entities);
//
//        Assertions.assertThat(redactedContent).isEqualTo(expectedContent);
//    }


}
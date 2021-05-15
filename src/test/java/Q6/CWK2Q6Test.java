package Q6;

import Q1.CWK2Q1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CWK2Q6Test {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams(){
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams(){
        System.setOut(originalOut);
    }

    @Test
    public void out(){
        System.out.print("hello");
        assertThat(outContent.toString()).isEqualTo("hello");
    }

    @Test
    void redactWords() {
        String inputFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleFile.txt";
        String redactFile = "/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleRedact.txt";
        String expected = "It was in ****, 1805, and the speaker was the well-known **** ******** *******, maid of honor and favorite of the ******* ***** *********. With these words she greeted ****** ****** *******, a man of high rank and importance, who was the first to arrive at her reception. **** ******** had had a cough for some days. She was, as she said, suffering from la grippe; grippe being then a new word in *** **********, used only by the elite.";

        CWK2Q6.redactWords(inputFile, redactFile);
        assertThat(outContent.toString().trim()).isEqualTo(expected);
    }

    @Test
    void checkReadFile(){
        String fileContent = CWK2Q6.readFile("/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleFile.txt");
        System.out.println(fileContent);

        String redactWords = CWK2Q6.readFile("/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleRedact.txt");
        String[] entities = redactWords.split(",");
        for (String entity : entities) {
            System.out.println(entity.trim());
        }
        //TODO: how do we  write test for this?
    }

    @Test
    void properNouns(){
        String fileContent = CWK2Q6.readFile("/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleFile.txt");

        List<String> properNouns = CWK2Q6.getOtherProperNouns(fileContent);
        for (String properNoun : properNouns) {
            System.out.println(properNoun);
        }
        //TODO: what should the assertions be?
    }

    @Test
    void replace(){
        String inputContent = "It was in July, 1805, and the speaker was the well-known Anna Pavlovna Scherer, maid of honor and favorite of the Empress Marya Fedorovna.";
        String expectedContent = "It was in July, 1805, and the speaker was the well-known **** ******** Scherer, maid of honor and favorite of the Empress Marya Fedorovna.";
        ArrayList<String> entities = new ArrayList<>(Arrays.asList("Anna","Pavlovna"));
        String redactedContent = CWK2Q6.replaceWordsInFile(inputContent, entities);

        Assertions.assertThat(redactedContent).isEqualTo(expectedContent);
    }


}
package Q6;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class CWK2Q6Test {

    @Test
    void redactWords() {
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
    }

    @Test
    void properNouns(){
        String fileContent = CWK2Q6.readFile("/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q6/exampleFile.txt");

        CWK2Q6.getOtherProperNouns(fileContent);
    }

}
package Q2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class CWK2Q2Test {
    @Test
    public void interpolationSearch_example1() {
        List<String> words = asList("Hello", "World", "How", "Are", "You");
        ArrayList<String> array = new ArrayList<>(words);

        assertThat(CWK2Q2.interpolation_search(array, "Are")).isEqualTo(0);
        assertThat(CWK2Q2.interpolation_search(array, "Hello")).isEqualTo(1);
        assertThat(CWK2Q2.interpolation_search(array, "How")).isEqualTo(2);
        assertThat(CWK2Q2.interpolation_search(array, "World")).isEqualTo(3);
        assertThat(CWK2Q2.interpolation_search(array, "You")).isEqualTo(4);
    }

    @Test
    public void interpolationSearch_example2() {
        List<String> words = asList("Ma", "Mar", "Mary", "Marianne", "Marianna", "Mariann", "Rosymary");
        ArrayList<String> array = new ArrayList<>(words);

        assertThat(CWK2Q2.interpolation_search(array, "Ma")).isEqualTo(0);
        assertThat(CWK2Q2.interpolation_search(array, "Mar")).isEqualTo(1);
        assertThat(CWK2Q2.interpolation_search(array, "Mariann")).isEqualTo(2);
        assertThat(CWK2Q2.interpolation_search(array, "Marianna")).isEqualTo(3);
        assertThat(CWK2Q2.interpolation_search(array, "Marianne")).isEqualTo(4);
        assertThat(CWK2Q2.interpolation_search(array, "Mary")).isEqualTo(5);
        assertThat(CWK2Q2.interpolation_search(array, "Rosymary")).isEqualTo(6);
    }

    @Test
    void checkInterpolationSearch() throws FileNotFoundException {
        String item = "MARY";
        String[] words = getStringFromFile();

        ArrayList<String> testList = new ArrayList<String>();
        testList.addAll(asList(words));

        assertThat(CWK2Q2.interpolation_search(testList, item)).isEqualTo(3362);
    }

    @Test
    void checkStringIsReadProperly() throws FileNotFoundException {
        String[] words = getStringFromFile();

        assertThat(words[0]).isEqualTo("MARY");
        assertThat(words[1]).isEqualTo("PATRICIA");
    }

    public String[] getStringFromFile() throws FileNotFoundException {
        File file = new File("/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q2/names.txt");
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\\Z");
        String s = sc.next();
        String[] words = s.split(",");

        System.out.println("Total count is: " + words.length);
        System.out.println("Unique count is: " + Arrays.stream(words).distinct().count());
        Arrays.setAll(words, i -> words[i].substring(1, words[i].length() - 1));
//        Arrays.setAll(words, i -> words[i].replaceFirst("^\"(.*)\"$", "$1"));
        return words;
    }
}
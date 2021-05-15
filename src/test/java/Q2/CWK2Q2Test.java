package Q2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CWK2Q2Test {
    @Test
    public void interpolationSearch_example1(){
        List<String> words = asList("Hello", "World", "How", "Are", "You");
        ArrayList<String> array = new ArrayList<>(words);

        assertThat( CWK2Q2.interpolation_search(array, "Are")).isEqualTo(0);
        assertThat( CWK2Q2.interpolation_search(array, "Hello")).isEqualTo(1);
        assertThat( CWK2Q2.interpolation_search(array, "How")).isEqualTo(2);
        assertThat( CWK2Q2.interpolation_search(array, "World")).isEqualTo(3);
        assertThat( CWK2Q2.interpolation_search(array, "You")).isEqualTo(4);
    }

    @Test
    public void interpolationSearch_example2(){
        List<String> words = asList("Ma", "Mar", "Mary", "Marianne", "Marianna", "Mariann", "Rosymary");
        ArrayList<String> array = new ArrayList<>(words);

        assertThat( CWK2Q2.interpolation_search(array, "Ma")).isEqualTo(0);
        assertThat( CWK2Q2.interpolation_search(array, "Mar")).isEqualTo(1);
        assertThat( CWK2Q2.interpolation_search(array, "Mariann")).isEqualTo(2);
        assertThat( CWK2Q2.interpolation_search(array, "Marianna")).isEqualTo(3);
        assertThat( CWK2Q2.interpolation_search(array, "Marianne")).isEqualTo(4);
        assertThat( CWK2Q2.interpolation_search(array, "Mary")).isEqualTo(5);
        assertThat( CWK2Q2.interpolation_search(array, "Rosymary")).isEqualTo(6);
    }




    @Test
    void checkConvertedLetterHasRightValue(){
        String s = "a";
        int expected = s.toLowerCase().hashCode() - 96;
        int actual = CWK2Q2.convertString(s);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkInterpolationSearch() throws FileNotFoundException {
        String item = "MARY";
        String[] words = getStringFromFile();

        ArrayList<String> testList = new ArrayList<String>();
        for ( String word: words){
            testList.add(word);
        }

        //FIXME:
        assertThat(CWK2Q2.interpolation_search(testList, item)).isEqualTo(3362);
    }

    @Test
    void checkStringIsReadProperly() throws FileNotFoundException {
        String[] words = getStringFromFile();

        assertThat(words[0]).isEqualTo("MARY");
        assertThat(words[1]).isEqualTo("PATRICIA");
    }

    public String[] getStringFromFile() throws FileNotFoundException {
        File file=new File("/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q2/names.txt");
        Scanner sc=new Scanner(file);
        sc.useDelimiter("\\Z");
        String s = sc.next();
        String[] words = s.split(",");

        System.out.println("Total count is: " + words.length);
        long uniqueCount = Arrays.stream(words).distinct().count();
        System.out.println("Unique count is: " + uniqueCount);
        Arrays.setAll(words, i -> words[i].substring(1, words[i].length()-1));
//        Arrays.setAll(words, i -> words[i].replaceFirst("^\"(.*)\"$", "$1"));
        return words;
    }

    @Test
    void check(){
        System.out.println(CWK2Q2.scaledCharDifference("Mar", "Mary"));
    }
}
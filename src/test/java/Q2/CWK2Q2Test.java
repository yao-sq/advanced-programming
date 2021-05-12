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
        ArrayList<String> array = new ArrayList<>();
        for (String word: words) {
            array.add(word);
        }

        assertThat( CWK2Q2.interpolation_search(array, "Are")).isEqualTo(0);
        assertThat( CWK2Q2.interpolation_search(array, "Hello")).isEqualTo(1);
        assertThat( CWK2Q2.interpolation_search(array, "How")).isEqualTo(2);
        assertThat( CWK2Q2.interpolation_search(array, "World")).isEqualTo(3);
        assertThat( CWK2Q2.interpolation_search(array, "You")).isEqualTo(4);
    }

    @Test
    void checkConvertedStringHasRightValue(){
        String s = "Z";
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
        assertThat(CWK2Q2.interpolation_search(testList, item)).isEqualTo(0);
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
        Arrays.setAll(words, i -> words[i].substring(1, words[i].length()-1));
//        Arrays.setAll(words, i -> words[i].replaceFirst("^\"(.*)\"$", "$1"));
        return words;
    }
}
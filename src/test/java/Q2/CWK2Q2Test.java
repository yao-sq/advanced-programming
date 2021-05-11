package Q2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CWK2Q2Test {
    @Test
    void checkConvertedStringHasRightValue(){
        String s = "Z";
        int expected = s.toLowerCase().hashCode() -96;
        int actual = CWK2Q2.convertString(s);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkInterpolationSearch() throws FileNotFoundException {
        String item = "\"MARY\"";
        String[] words = getStringFromFile();

        ArrayList<String> testList = new ArrayList<String>();
        for ( String word: words){
            testList.add(word);
        }

        Map<String, Integer> unsortedIndexOf = CWK2Q2.letterToNumber(testList);

        int actualUnsortedPosition = -1;
        int actualSortedPosition = CWK2Q2.interpolation_search(testList, item);
        if (actualSortedPosition != -1){
            actualUnsortedPosition = unsortedIndexOf.get(item);
        }
        Assertions.assertThat(actualUnsortedPosition).isEqualTo(0);
    }

    @Test
    void checkStringIsReadProperly() throws FileNotFoundException {
        String[] words = getStringFromFile();

        Assertions.assertThat(words[0]).isEqualTo("\"MARY\"");
        Assertions.assertThat(words[1]).isEqualTo("\"PATRICIA\"");
    }

    public String[] getStringFromFile() throws FileNotFoundException {
        File file=new File("/Users/yao/IdeaProjects/advanced-programming/src/main/java/Q2/names.txt");
        Scanner sc=new Scanner(file);
        sc.useDelimiter("\\Z");
        String s = sc.next();
        String[] words = s.split(",");
        return words;
    }
}
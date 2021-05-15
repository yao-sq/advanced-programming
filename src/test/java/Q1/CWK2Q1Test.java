package Q1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toCollection;
import static org.assertj.core.api.Assertions.assertThat;

class CWK2Q1Test {
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
    public void outputCaptureWorks(){
        System.out.print("hello");
        assertThat(outContent.toString()).isEqualTo("hello");
    }

    @Test
    void shell_sort_ultimatelyPrintsTheCorrectlySortedArray_forSmallIntegerArrays(){
        List<Double> expectedSorted = convertArrayIntegerToDouble(asList(2, 24, 27, 34, 61, 109, 111, 119, 122, 125, 145, 149));
        CWK2Q1.shell_sort(new ArrayList<Double>(convertArrayIntegerToDouble(asList(61, 109, 149, 111, 34, 2, 24, 119, 122, 125, 27, 145))));

        assertThat(getOutputLines()).last().isEqualTo(expectedSorted.toString());
    }

    @Test
    void shell_sort_ultimatelyPrintsTheCorrectlySortedArray_forSmallDoubleArrays(){
        List<Double> expectedSorted = asList(-23.6, -12.2, -4.18, 1.73, 3.4, 6.55, 52.87, 73.202, 99.14, 140.98);
        CWK2Q1.shell_sort(new ArrayList<Double>(asList(3.4, 6.55, -12.2, 1.73, 140.98, -4.18, 52.87, 99.14, 73.202, -23.6)));

        assertThat(getOutputLines()).last().isEqualTo(expectedSorted.toString());
    }

    @Test
    void shell_sort_ultimatelyPrintsTheCorrectlySortedArray_forBigIntegerArrays(){
        ArrayList<Double> testList = new ArrayList<Double>();
        Random rand = new Random();
        for (int k = 0; k < 1000; k++) {
            int pick = rand.nextInt(88) + 13;
            testList.add((double) pick);
        }

        CWK2Q1.shell_sort(testList);

        testList.sort(Double::compareTo);
        assertThat(getOutputLines()).last().isEqualTo(testList.toString());
    }

    private List<String> getOutputLines() {
        return asList(outContent.toString().split("\\R"));
    }

    private static List<Double> convertArrayIntegerToDouble(List<Integer> integers) {
        return integers.stream().map(i -> (double) i).collect(toCollection(ArrayList::new));
    }
}
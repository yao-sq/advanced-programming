package Q1;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void out(){
        System.out.print("hello");
        assertThat(outContent.toString()).isEqualTo("hello");
    }

    @Test
    void checkFinalResultForShortIntegerArrays(){
        List<Double> expectedSorted = convertArrayIntegerToDouble(Arrays.asList(2, 24, 27, 34, 61, 109, 111, 119, 122, 125, 145, 149));
        CWK2Q1.shell_sort(new ArrayList<Double>(convertArrayIntegerToDouble(Arrays.asList(61, 109, 149, 111, 34, 2, 24, 119, 122, 125, 27, 145))));

        assertThat(outContent.toString()).isEqualTo(expectedSorted.toString());
    }

    private List<Double> convertArrayIntegerToDouble(List<Integer> integers) {
        List<Double> doubles = new ArrayList<Double>();
        for (Integer i: integers) {
            Double d = new Double(i);
            doubles.add(d);
        }
        return doubles;
    }


    @Test
    void checkFinalResultForShortDoubleArrays(){
        List<Double> expectedSorted = Arrays.asList(-23.6, -12.2, -4.18, 1.73, 3.4, 6.55, 52.87, 73.202, 99.14, 140.98);
        CWK2Q1.shell_sort(new ArrayList<Double>(Arrays.asList(3.4, 6.55, -12.2, 1.73, 140.98, -4.18, 52.87, 99.14, 73.202, -23.6)));

        assertThat(outContent.toString()).isEqualTo(expectedSorted.toString());
    }
}
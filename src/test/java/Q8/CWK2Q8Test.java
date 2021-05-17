package Q8;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class CWK2Q8Test {
    @Test
    void howManyTuesday_inYear_2000() {
        int expected = 2;
        LocalDate start = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2000, Month.DECEMBER, 31);

        assertThat(CWK2Q8.howManyTuesday(start, end)).isEqualTo(expected);
    }

    @Test
    void howManyTuesday_inYear_2020() {
        int expected = 2;
        LocalDate start = LocalDate.of(2020, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2020, Month.DECEMBER, 31);

        assertThat(CWK2Q8.howManyTuesday(start, end)).isEqualTo(expected);
    }

    @Test
    void howManyTuesday_inYear_1995_1999() {
        int expected = 7;
        LocalDate start = LocalDate.of(1995, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(1999, Month.DECEMBER, 31);

        assertThat(CWK2Q8.howManyTuesday(start, end)).isEqualTo(expected);
    }

    @Test
    void howManyTuesday_inYear_1901_2000() {
        int expected = 171;
        LocalDate start = LocalDate.of(1901, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2000, Month.DECEMBER, 31);

        assertThat(CWK2Q8.howManyTuesday(start, end)).isEqualTo(expected);
    }

    @Test
    void howManyTuesday_inYear_1900_2900() {
        int expected = 1718;
        LocalDate start = LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2900, Month.DECEMBER, 31);

        assertThat(CWK2Q8.howManyTuesday(start, end)).isEqualTo(expected);
    }
}
package Q8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * @author Anonymous (do not change)
 * <p>
 * Question 8:
 * <p>
 * You are given the following information, but you may prefer
 * to do some research for yourself.
 * •	1 Jan 1900 was a Monday.
 * •	Thirty days has September, April, June and November. All the rest
 * have thirty-one, saving February alone, which has twenty-eight, rain
 * or shine. And on leap years, twenty-nine.
 * •	A leap year occurs on any year evenly divisible by 4, but not on a
 * century unless it is divisible by 400.
 * <p>
 * How many Tuesdays fell on the first of the month during the twentieth
 * century (1 Jan 1901 to 31 Dec 2000)?
 * <p>
 * Note, this problem is inspired by Project Euler so, as stated in the
 * rules of Project Euler, your solution should return an answer under
 * 60 seconds.
 */

public class CWK2Q8 {
    public static int howManyTuesdays() {
        LocalDate t = LocalDate.of(1901, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2000, Month.DECEMBER, 31);

        return howManyTuesday(t, end);
    }

    public static int howManyTuesday(LocalDate t, LocalDate end) {
        int tuesdays = 0;
        while (!t.isAfter(end)) {
            if (t.getDayOfMonth() == 1 && DayOfWeek.TUESDAY.equals(t.getDayOfWeek())) {
                tuesdays++;
            }
            t = t.plusDays(1);
        }
        //Tuesdays: 5218
        //First of month Tuesdays: 171
        return tuesdays;
    }

    public static void main(String[] args) {
        int result = CWK2Q8.howManyTuesdays();
        System.out.println("Number of Tuesdays = " + result);
    }
}

package Q1;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Anonymous (do not change)
 * <p>
 * Question 1:
 * <p>
 * Implement the Shellsort algorithm (https://en.wikipedia.org/wiki/Shellsort)
 * for an array of up to 1000 signed doubles in Java. Your solution must use
 * concrete gaps of [1, 3, 7, 15, 31, 63, 127, 255, 511]. Your solution must
 * print the (partially) sorted array after each gap on a new line in the form:
 * [a0, a1, a2, a3, ..., an]
 * Where an is the nth element in the (partially) sorted array (please note
 * the space after the commas), and each element should be formatted to 2
 * decimal places (e.g. 1.00).
 */

public class CWK2Q1 {
    public static void shell_sort(ArrayList<Double> array) {
        /* An utility function to print array of size n*/

        /* function to sort array using shellSort */
        int n = array.size();

        // Start with a larger gap, then reduce the gap to 1
        // we take gap sequence in order of |N/2|, |N/4|, |N/8|...1
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // we perform gapped insertion sort for this gap size.
            // The first gap elements a[0..gap-1] are already
            // in gapped order keep adding one more element
            // until the entire array is gap sorted
            for (int i = gap; i < n; i += 1) {
                // store a[i] in temp and make a hole at
                // position i
                double temp = array.get(i);
                // shift earlier gap-sorted elements up until
                // the correct location for a[i] is found
                int j;
                for (j = i; j >= gap && array.get(j - gap) > temp; j -= gap)
                    array.set(j, array.get(j - gap));

                // put temp (the original a[i]) in its correct
                // location
                array.set(j, temp);
            }
//            System.out.println(array);
        }
		System.out.print(array);
    }

    public static void main(String[] args) {
        ArrayList<Double> testList = new ArrayList<Double>();
        testList.add(3.4);
        testList.add(6.55);
        testList.add(-12.2);
        testList.add(1.73);
        testList.add(140.98);
        testList.add(-4.18);
        testList.add(52.87);
        testList.add(99.14);
        testList.add(73.202);
        testList.add(-23.6);

        shell_sort(testList);
    }
}

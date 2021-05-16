package Q2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author Anonymous (do not change)
 * <p>
 * Question 2:
 * <p>
 * Implement interpolation search for a list of Strings in Java
 * using the skeleton class provided. The method should return
 * the position in the array if the string is present, or -1 if
 * it is not present.
 */

public class CWK2Q2<T> {
    public static int interpolation_search(ArrayList<String> array, String item) {
        Collections.sort(array);
        System.out.println("Using interpolation search");
        return interpolation_search(array, item, CWK2Q2::scaledCharDifference);
    }

    public static <T extends Comparable<T>> int interpolation_search(ArrayList<T> array, T item, BiFunction<T, T, Integer> minus) {
        return interpolation_search(array, item, minus, Comparator.naturalOrder());
    }

    public static <T> int interpolation_search(ArrayList<T> array, T item, BiFunction<T, T, Integer> minus, Comparator<T> comparator) {
        int low = 0;
        int high = array.size() - 1;
        int mid;

        while (!Objects.equals(array.get(high), array.get(low))
                && comparator.compare(item, array.get(low)) >= 0
                && comparator.compare(item, array.get(high)) <= 0
        ) {
            mid = low + (minus.apply(item, array.get(low))
                    * ((high - low) / minus.apply(array.get(high), array.get(low))));

            if (comparator.compare(array.get(mid), item) < 0) {
                low = mid + 1;
            } else if (comparator.compare(item, array.get(mid)) < 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }

        if (Objects.equals(item, array.get(low))) {
            return low;
        }

        return -1;
    }

    public static int scaledCharDifference(String a, String b) {
        int commonLength = Math.min(a.length(), b.length());
        for (int i = 0; i < commonLength; i++) {
            int diff = Character.compare(a.charAt(i), b.charAt(i));
            if (diff != 0) {
                return diff * (82595524 - i);
            }
        }
        return Integer.compare(a.length(), b.length());
    }

    public static void main(String[] args) {
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("Hello");
        testList.add("World");
        testList.add("How");
        testList.add("Are");
        testList.add("You");

        System.out.println(interpolation_search(testList, "Are"));
    }
}

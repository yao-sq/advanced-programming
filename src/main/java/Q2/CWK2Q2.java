package Q2;

import java.util.*;
import java.lang.Math;

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

public class CWK2Q2 {
    public static int interpolation_search(ArrayList<String> array, String item) {
        Map<String, Integer> sortedIndexOf;

        // Need to sort array
        Collections.sort(array);
        sortedIndexOf = letterToNumber(array);
        return interpolation_search(array, item, sortedIndexOf);

    }

    public static int interpolation_search(ArrayList<String> array, String item, Map<String, Integer> sortedIndexOf) {
        int low = 0;
        int high = array.size() - 1;
        int mid;

        while (  sortedIndexOf.get(array.get(high)) != sortedIndexOf.get(array.get(low))
                &&  sortedIndexOf.get(item) >= sortedIndexOf.get(array.get(low))
                &&  sortedIndexOf.get(item) <= sortedIndexOf.get(array.get(high))
        ){
            mid = low + ((sortedIndexOf.get(item) - sortedIndexOf.get(array.get(low))) * (high - low) / (sortedIndexOf.get(array.get(high)) - sortedIndexOf.get(array.get(low))));

            if (sortedIndexOf.get(array.get(mid)) < sortedIndexOf.get(item))
                low = mid + 1;
            else if (sortedIndexOf.get(item) < sortedIndexOf.get(array.get(mid)))
                high = mid - 1;
            else
                return mid;
        }
        if (sortedIndexOf.get(item) == sortedIndexOf.get(array.get(low)))
            return low;
        else
            return -1;
    }


    public static int convertString(String s) {
        return s.toLowerCase().hashCode() - 96;
    }

    public static double convertWord(String s) {
        int length = s.length();
        double sum = 0;
        for (int i = 0; i < length; i++) {
            int value = convertString(s.substring(i, i + 1));
            System.out.println(value);
            sum = sum + value * Math.pow(10.0, length - i - 1);
        }
        return sum;
    }

    public static Map<String, Integer> letterToNumber(ArrayList<String> testList) {
        Map<String, Integer> getIndex = new HashMap<String, Integer>();
        for (int i = 0; i < testList.size(); i++) {
            getIndex.put(testList.get(i), i);
        }
        return getIndex;
    }

    public static void main(String[] args) {
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("Hello");
        testList.add("World");
        testList.add("How");
        testList.add("Are");
        testList.add("You");

        // Remember initial positions
        Map<String, Integer> unsortedIndexOf = letterToNumber(testList);


        int result = -1;
        int sortedIndex = interpolation_search(testList, "How");
        if (sortedIndex != -1) {
            result = unsortedIndexOf.get("How");
        }
        System.out.println("Result = " + result);

    }

}

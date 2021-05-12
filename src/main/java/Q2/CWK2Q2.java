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
        // Work with a copy so the user array is not modified
        array = new ArrayList<>(array);
        List<Map.Entry<String, Integer>> entries = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            String value = array.get(i);
            entries.add(new AbstractMap.SimpleEntry<String, Integer>(value, i));
        }
        entries.sort(Map.Entry.comparingByKey());
        return interpolation_search(array, item, entries);

    }

    private static int interpolation_search(ArrayList<String> array, String item, List<Map.Entry<String, Integer>> entries) {
        int low = 0;
        int high = array.size() - 1;
        int mid;
        int result;

        Collections.sort(array);
        Map<String, Integer> sortedIndexOf = letterToNumber(array);
//        System.out.println(sortedIndexOf);

        Integer itemValue = sortedIndexOf.get(item);
        while (  sortedIndexOf.get(array.get(high)) != sortedIndexOf.get(array.get(low))
                &&  itemValue >= sortedIndexOf.get(array.get(low))
                &&  itemValue <= sortedIndexOf.get(array.get(high))
        ){
            mid = low + ((itemValue - sortedIndexOf.get(array.get(low))) * (high - low) / (sortedIndexOf.get(array.get(high)) - sortedIndexOf.get(array.get(low))));

            if (sortedIndexOf.get(array.get(mid)) < itemValue)
                low = mid + 1;
            else if (itemValue < sortedIndexOf.get(array.get(mid)))
                high = mid - 1;
            else

                return entries.get(mid).getValue();
        }

        if (itemValue == sortedIndexOf.get(array.get(low)))
            return entries.get(low).getValue();
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

        System.out.println(interpolation_search(testList, "Are"));
    }

}

package br.com.extractor.ygops.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Muryllo Tiraza on 11/02/2016.
 */
public class MapUtils {

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> map) {
        Object[] objects = map.entrySet().toArray();
        Arrays.sort(objects, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue().compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });

        HashMap<String, Integer> result = new HashMap<>();
        for (Object object : objects) {
            result.put(((Map.Entry<String, Integer>) object).getKey(), ((Map.Entry<String, Integer>) object).getValue());
        }

        return result;
    }

}

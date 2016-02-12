package br.com.extractor.ygops.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.extractor.ygops.model.ItemCount;

/**
 * Created by Muryllo Tiraza on 11/02/2016.
 */
public class MapUtils {

    public static List<ItemCount> sortByValue(HashMap<String, Integer> map) {
        Object[] objects = map.entrySet().toArray();
        Arrays.sort(objects, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue().compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });

        List<ItemCount> list = new ArrayList<>();
        for (Object object : objects) {
            ItemCount item = new ItemCount();
            item.setNome(((Map.Entry<String, Integer>) object).getKey().toString());
            item.setQuantidade(((Map.Entry<String, Integer>) object).getValue().intValue());
            list.add(item);
        }

        return list;
    }

}

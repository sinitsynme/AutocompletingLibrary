package ru.sinitsynme.airportsearch.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtils<K, V> {

    public Map<K, V> filter(Map<K, V> map, List<K> keys){
        Map<K, V> resultMap = new HashMap<>();

        for (K key: keys){
            resultMap.put(key, map.get(key));
        }

        return resultMap;
    }
}

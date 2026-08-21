package org.example;

import org.junit.Test;

import java.util.*;

public class TreeMap_ComparableTest {
    @Test public void testTreeMap() {
        TreeMap<SortedSet_Comparable, String> map = new TreeMap<>();
        SortedSet_Comparable obj = new SortedSet_Comparable();
        map.put(obj, "value");

        Map<SortedSet_Comparable, String> map2 = new HashMap<>();
        map2.put(obj, "value");

        TreeMap<SortedSet_Comparable, String> map3 = new TreeMap<>(map2);

        TreeMap<SortedSet_Comparable, String> map4 = new TreeMap<>();
        map4.putAll(map2);
    }
}

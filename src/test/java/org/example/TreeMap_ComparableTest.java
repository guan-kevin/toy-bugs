package org.example;

import org.junit.Test;

import java.util.*;

public class TreeMap_ComparableTest {
    @Test public void testTreeMap() {
        TreeMap<SortedSet_Comparable, String> map = new TreeMap<>();
        SortedSet_Comparable obj = new SortedSet_Comparable();
        map.put(obj, "value");
    }

    @Test public void testTreeMap2() {
        SortedSet_Comparable obj = new SortedSet_Comparable();
        Map<SortedSet_Comparable, String> map2 = new HashMap<>();
        map2.put(obj, "value");

        TreeMap<SortedSet_Comparable, String> map3 = new TreeMap<>(map2);
    }

    @Test public void testTreeMap3() {
        SortedSet_Comparable obj = new SortedSet_Comparable();
        Map<SortedSet_Comparable, String> map2 = new HashMap<>();
        map2.put(obj, "value");
        TreeMap<SortedSet_Comparable, String> map4 = new TreeMap<>();
        map4.putAll(map2);
    }
}

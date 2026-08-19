package org.example;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSet_ComparableTest {
    @Test public void testSortedSet() {
        SortedSet<SortedSet_Comparable> set = new TreeSet<>();
        SortedSet_Comparable obj = new SortedSet_Comparable();
        set.add(obj);
    }

    @Test public void testSortedSet2() {
        SortedSet<SortedSet_Comparable> set = new TreeSet<>();
        Set<SortedSet_Comparable> set2 = new HashSet<>();
        SortedSet_Comparable obj = new SortedSet_Comparable();
        set2.add(obj);
        set.addAll(set2);
    }
}

package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityQueue_NonComparableTest {
    @Test public void testNonComparable() {
        PriorityQueue<SortedSet_Comparable> set = new PriorityQueue<>();
        SortedSet_Comparable obj = new SortedSet_Comparable();
        set.add(obj);

        List<SortedSet_Comparable> list = new ArrayList<>();
        list.add(obj);
        PriorityQueue<SortedSet_Comparable> set2 = new PriorityQueue<>();
        set2.addAll(list);
    }
}

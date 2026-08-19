package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Collection_UnsafeIteratorTest {
    @Test public void testUnsafeIterator() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");

        Iterator<String> iter = list.iterator();
        iter.next();
        list.add("three"); // Modifying the list while iterating
        iter.next(); // This will throw ConcurrentModificationException
    }
}

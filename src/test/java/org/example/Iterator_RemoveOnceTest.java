package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Iterator_RemoveOnceTest {
    @Test public void testRemoveOnce() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");

        Iterator<String> iter = list.iterator();
        iter.next();
        iter.remove();
        iter.remove();
    }
}

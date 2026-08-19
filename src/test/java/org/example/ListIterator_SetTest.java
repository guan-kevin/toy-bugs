package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.junit.Test;

public class ListIterator_SetTest {
    @Test public void testSet() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");

        ListIterator<String> it = list.listIterator();

        // VIOLATION:
        // set() is called without a preceding next() or previous()
        it.set("X");
    }
}

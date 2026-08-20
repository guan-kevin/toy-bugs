package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class Iterator_HasNextTest {
    @Test public void testHasNext() {
        List<String> list = new ArrayList<>();
        list.add("A");
        assertEquals("A", new Iterator_HasNext().getFirstExpected(list));

        list = new ArrayList<>();
        list.add("A");
        assertEquals("A", new Iterator_HasNext().getFirstSafe(list));

        list = new ArrayList<>();
        list.add("A");
        assertEquals("A", new Iterator_HasNext().getFirstSafe2(list));

        list = new ArrayList<>();
        list.add("A");
        assertEquals("A", new Iterator_HasNext().getSafeUnsafe(list));
    }
}

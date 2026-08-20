package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class Iterator_HasNextTest {
    @Test public void testHasNext() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");

        assertEquals("A", new Iterator_HasNext().getFirstExpected(list));
        assertEquals("A", new Iterator_HasNext().getFirstSafe(list));
        assertEquals("A", new Iterator_HasNext().getFirstSafe2(list));
        assertEquals("A", new Iterator_HasNext().getSafeUnsafe(list));
    }
}

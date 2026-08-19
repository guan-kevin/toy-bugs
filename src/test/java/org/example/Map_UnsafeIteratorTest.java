package org.example;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Map_UnsafeIteratorTest {
    @Test
    public void testUnsafeIterator() {
        Map<String, Integer> map = new HashMap<>();

        map.put("A", 1);
        map.put("B", 2);

        // getset
        Set<String> keys = map.keySet();

        // getiter
        Iterator<String> it = keys.iterator();

        // useiter
        it.hasNext();

        // modifyMap2:
        // "C" is a new key, so !map.containsKey("C") is true
        map.put("C", 3);

        // useiter again -> VIOLATION
        it.hasNext();
    }
}

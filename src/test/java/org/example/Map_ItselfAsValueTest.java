package org.example;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class Map_ItselfAsValueTest {

    @Test
    public void testMapContainsItselfAsValue() {
        Map<String, Object> map = new HashMap<>();
        map.put("self", map);
        assertSame(map, map.get("self"));
    }
}

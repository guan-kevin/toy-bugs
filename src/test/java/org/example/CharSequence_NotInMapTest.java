package org.example;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CharSequence_NotInMapTest {

    @Test public void testNotInMap() {
        Map<CharSequence, String> map = new HashMap<>();
        map.put((new CharSequence_UndefinedHashCode()).new MyCharSequence(), "value");
    }
}

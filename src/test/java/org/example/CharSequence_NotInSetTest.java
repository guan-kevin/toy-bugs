package org.example;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CharSequence_NotInSetTest {

    @Test public void testNotInSet() {
        Set<CharSequence> set = new HashSet<>();
        set.add((new CharSequence_UndefinedHashCode()).new MyCharSequence());

        Set<CharSequence> set2 = new HashSet<>();
        set2.addAll(set);
    }
}

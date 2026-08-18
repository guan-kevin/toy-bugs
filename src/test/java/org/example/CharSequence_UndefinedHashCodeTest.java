package org.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharSequence_UndefinedHashCodeTest {
    @Test public void testHashCode() {
        CharSequence_UndefinedHashCode obj = new CharSequence_UndefinedHashCode();
        obj.foo();
    }

    @Test public void testEquals() {
        CharSequence_UndefinedHashCode obj = new CharSequence_UndefinedHashCode();
        obj.bar();
    }
}

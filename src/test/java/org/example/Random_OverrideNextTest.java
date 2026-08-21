package org.example;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class Random_OverrideNextTest {

    static class BadRandom extends Random {

        private static final long serialVersionUID = 1L;

        @Override
        public int nextInt(int bound) {
            // Customizes Random behavior but does not override next(int)
            return super.nextInt(bound);
        }
    }

    @Test
    public void testInheritedNextLongAfterOverridingRandomBehavior() {
        Random random = new BadRandom();

        // nextLong() is inherited from java.util.Random.
        // Random.nextLong() relies on next(int).
        long value = random.nextLong();

        // Prevent compiler optimization
        assertNotNull(Long.valueOf(value));
    }
}
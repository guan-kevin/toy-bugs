package org.example;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

public class InputStream_UnmarkedResetTest {
    @Test public void testUnmarkedReset() throws Exception {
        BufferedInputStream input =
                new BufferedInputStream(
                        new ByteArrayInputStream("hello".getBytes())
                );

        // JavaMOP violation should be reported here.
        input.reset();
    }
}

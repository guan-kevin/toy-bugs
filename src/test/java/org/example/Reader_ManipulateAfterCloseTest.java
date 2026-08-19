package org.example;

import org.junit.Test;

import java.io.PipedReader;
import java.io.Reader;

public class Reader_ManipulateAfterCloseTest {
    @Test
    public void testManipulateAfterClose() throws Exception {
        Reader reader = new PipedReader();

        reader.close();
        reader.read();
    }
}

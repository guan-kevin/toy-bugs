package org.example;

import org.junit.Test;

import java.io.FileWriter;
import java.io.Writer;

public class Writer_ManipulateAfterCloseTest {
    @Test
    public void testManipulateAfterClose() throws Exception {
        Writer writer = new FileWriter("test.txt");

        writer.close();
        writer.flush();
    }
}

package org.example;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class OutputStream_ManipulateAfterCloseTest {
    @Test
    public void testManipulateAfterClose() throws Exception {
        OutputStream out = new FileOutputStream("test.txt");

        out.close();
        out.write("hello".getBytes());
    }
}

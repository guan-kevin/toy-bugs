package org.example;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.codec.binary.Base32InputStream;
import org.apache.commons.codec.binary.Base64InputStream;
import org.junit.Test;

public class InputStream_ManipulateAfterCloseTest {

    @Test
    public void testFileInputStreamReadAfterClose() throws Exception {
        Path file = Files.createTempFile("test", ".txt");
        Files.write(file, new byte[] {1, 2, 3});

        InputStream in = new FileInputStream(file.toFile());

        in.close();

        // SPEC VIOLATION: close -> read
        try {
            in.read();
            fail("Expected IOException");
        } catch (IOException expected) {
            // expected
        }
    }

    @Test
    public void testBufferedInputStreamReadAfterClose() throws Exception {
        Path file = Files.createTempFile("test", ".txt");
        Files.write(file, new byte[] {1, 2, 3});

        InputStream in =
                new BufferedInputStream(
                        new FileInputStream(file.toFile()));

        in.close();

        // SPEC VIOLATION: close -> read
        try {
            in.read();
            fail("Expected IOException");
        } catch (IOException expected) {
            // expected
        }
    }

    @Test
    public void testBase64WrappingFileInputStreamReadAfterClose()
            throws Exception {

        Path file = Files.createTempFile("test", ".txt");
        Files.write(file, "YWJj".getBytes("US-ASCII"));

        InputStream in =
                new Base64InputStream(
                        new FileInputStream(file.toFile()));

        in.close();

        // SPEC VIOLATION:
        // Base64InputStream -> FileInputStream
        try {
            in.read();
            fail("Expected IOException");
        } catch (IOException expected) {
            // expected
        }
    }

    @Test
    public void testNestedBase64RootedInFileInputStream()
            throws Exception {

        Path file = Files.createTempFile("test", ".txt");
        Files.write(file, "YWJj".getBytes("US-ASCII"));

        InputStream in =
                new Base64InputStream(
                        new FileInputStream(file.toFile()));

        in = new Base64InputStream(in);

        in.close();

        // SPEC VIOLATION:
        //
        // Base64InputStream
        //       |
        // Base64InputStream
        //       |
        // FileInputStream
        try {
            in.read();
            fail("Expected IOException");
        } catch (IOException expected) {
            // expected
        }
    }

    @Test
    public void testNestedBase32Base64RootedInFileInputStream()
            throws Exception {

        Path file = Files.createTempFile("test", ".txt");
        Files.write(file, "MFRGG===".getBytes("US-ASCII"));

        InputStream in =
                new Base32InputStream(
                        new FileInputStream(file.toFile()));

        in = new Base64InputStream(in);
        in = new Base32InputStream(in, false);

        in.close();

        // SPEC VIOLATION:
        //
        // Base32InputStream
        //       |
        // Base64InputStream
        //       |
        // Base32InputStream
        //       |
        // FileInputStream
        try {
            in.read();
            fail("Expected IOException");
        } catch (IOException expected) {
            // expected
        }
    }

    @Test
    public void testAvailableAfterClose() throws Exception {
        Path file = Files.createTempFile("test", ".txt");
        Files.write(file, new byte[] {1, 2, 3});

        InputStream in =
                new BufferedInputStream(
                        new FileInputStream(file.toFile()));

        in.close();

        // SPEC VIOLATION: close -> available
        try {
            in.available();
            fail("Expected IOException");
        } catch (IOException expected) {
            // expected
        }
    }

    @Test
    public void testSkipAfterClose() throws Exception {
        Path file = Files.createTempFile("test", ".txt");
        Files.write(file, new byte[] {1, 2, 3});

        InputStream in =
                new BufferedInputStream(
                        new FileInputStream(file.toFile()));

        in.close();

        // SPEC VIOLATION: close -> skip
        try {
            in.skip(1);
            fail("Expected IOException");
        } catch (IOException expected) {
            // expected
        }
    }

    @Test
    public void testResetAfterClose() throws Exception {
        Path file = Files.createTempFile("test", ".txt");
        Files.write(file, new byte[] {1, 2, 3});

        BufferedInputStream in =
                new BufferedInputStream(
                        new FileInputStream(file.toFile()));

        in.mark(10);
        in.close();

        // SPEC VIOLATION: close -> reset
        try {
            in.reset();
            fail("Expected IOException");
        } catch (IOException expected) {
            // expected
        }
    }
}
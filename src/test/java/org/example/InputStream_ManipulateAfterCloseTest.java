package org.example;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base32InputStream;
import org.apache.commons.codec.binary.Base64InputStream;
import org.junit.Test;

public class InputStream_ManipulateAfterCloseTest {

    @Test
    public void readAfterClose() throws Exception {
        StrictInputStream in = new StrictInputStream();

        in.close();

        // VIOLATION: close -> read
        try {
            in.read();
        } catch (IOException expected) {
        }
    }

    @Test
    public void availableAfterClose() throws Exception {
        StrictInputStream in = new StrictInputStream();

        in.close();

        // VIOLATION: close -> available
        try {
            in.available();
        } catch (IOException expected) {
        }
    }

    @Test
    public void skipAfterClose() throws Exception {
        StrictInputStream in = new StrictInputStream();

        in.close();

        // VIOLATION: close -> skip
        try {
            in.skip(1);
        } catch (IOException expected) {
        }
    }

    @Test
    public void resetAfterClose() throws Exception {
        StrictInputStream in = new StrictInputStream();

        in.close();

        // VIOLATION: close -> reset
        try {
            in.reset();
        } catch (IOException expected) {
        }
    }

    @Test
    public void multipleClosesThenRead() throws Exception {
        StrictInputStream in = new StrictInputStream();

        in.close();
        in.close();
        in.close();

        // VIOLATION:
        //
        // close close close read
        try {
            in.read();
        } catch (IOException expected) {
        }
    }

    @Test
    public void manipulateBeforeCloseThenReadAfterClose() throws Exception {
        StrictInputStream in = new StrictInputStream();

        // Valid.
        in.read();
        in.available();
        in.skip(1);

        in.close();

        // VIOLATION.
        try {
            in.read();
        } catch (IOException expected) {
        }
    }

    @Test
    public void base32RootedInStrictStream() throws Exception {
        StrictInputStream root = new StrictInputStream();

        Base32InputStream in =
                new Base32InputStream(root);

        in.close();

        // VIOLATION:
        //
        // Base32
        //   |
        // StrictInputStream
        try {
            in.read();
        } catch (IOException expected) {
        }
    }

    @Test
    public void base64RootedInStrictStream() throws Exception {
        StrictInputStream root = new StrictInputStream();

        Base64InputStream in =
                new Base64InputStream(root);

        in.close();

        // VIOLATION:
        //
        // Base64
        //   |
        // StrictInputStream
        try {
            in.read();
        } catch (IOException expected) {
        }
    }

    @Test
    public void nestedBase64RootedInStrictStream() throws Exception {
        InputStream in = new StrictInputStream();

        in = new Base64InputStream(in);
        in = new Base64InputStream(in);
        in = new Base64InputStream(in);

        in.close();

        // VIOLATION:
        //
        // Base64
        //   |
        // Base64
        //   |
        // Base64
        //   |
        // StrictInputStream
        try {
            in.read();
        } catch (IOException expected) {
        }
    }

    @Test
    public void mixedNestedBaseNRootedInStrictStream() throws Exception {
        InputStream in = new StrictInputStream();

        in = new Base64InputStream(in);
        in = new Base32InputStream(in);
        in = new Base64InputStream(in);
        in = new Base32InputStream(in, false);

        in.close();

        // VIOLATION:
        //
        // Base32
        //   |
        // Base64
        //   |
        // Base32
        //   |
        // Base64
        //   |
        // StrictInputStream
        try {
            in.read();
        } catch (IOException expected) {
        }
    }

    @Test
    public void deeplyNestedUnsafeBaseN() throws Exception {
        InputStream in = new StrictInputStream();

        for (int i = 0; i < 10; i++) {
            in = new Base32InputStream(
                    in,
                    true,
                    76,
                    new byte[] {'\r', '\n'});

            in = new Base32InputStream(in, false);
        }

        in.close();

        // VIOLATION.
        try {
            in.read();
        } catch (IOException expected) {
        }
    }
}
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

    @Test
    public void base64AvailableAfterClose() throws Exception {
        InputStream in =
                new Base64InputStream(new StrictInputStream());

        in.close();

        // VIOLATION:
        // baseConstruct -> closeBase -> available
        try {
            in.available();
        } catch (IOException expected) {
        }
    }

    @Test
    public void base32SkipAfterClose() throws Exception {
        InputStream in =
                new Base32InputStream(new StrictInputStream());

        in.close();

        // VIOLATION:
        // baseConstruct -> closeBase -> skip
        try {
            in.skip(1);
        } catch (IOException expected) {
        }
    }

    @Test
    public void base64ResetAfterClose() throws Exception {
        InputStream in =
                new Base64InputStream(new StrictInputStream());

        in.close();

        // VIOLATION:
        // baseConstruct -> closeBase -> reset
        try {
            in.reset();
        } catch (IOException expected) {
        }
    }

    @Test
    public void unsafeBase64ManipulateBeforeAndAfterClose() throws Exception {
        InputStream in =
                new Base64InputStream(new StrictInputStream());

        // Valid pre-close manipulations.
        in.read();
        in.available();
        in.skip(1);

        in.close();

        // VIOLATION:
        //
        // baseConstruct
        // manipulate*
        // closeBase
        // manipulate
        try {
            in.read();
        } catch (IOException expected) {
        }
    }

    @Test
    public void unsafeBase32MultipleClosesThenRead() throws Exception {
        InputStream in =
                new Base32InputStream(new StrictInputStream());

        in.close();
        in.close();
        in.close();

        // VIOLATION:
        //
        // baseConstruct -> closeBase+ -> manipulate
        try {
            in.read();
        } catch (IOException expected) {
        }
    }

    @Test
    public void mixedNestedUnsafeAvailableAfterClose() throws Exception {
        InputStream in = new StrictInputStream();

        in = new Base64InputStream(in);
        in = new Base32InputStream(in);
        in = new Base64InputStream(in);

        in.close();

        // VIOLATION:
        //
        // Base64
        //   |
        // Base32
        //   |
        // Base64
        //   |
        // StrictInputStream
        try {
            in.available();
        } catch (IOException expected) {
        }
    }

    @Test
    public void nestedUnsafeWithOverloadedConstructors() throws Exception {
        InputStream in = new StrictInputStream();

        in = new Base32InputStream(
                in,
                true,
                76,
                new byte[] {'\r', '\n'});

        in = new Base64InputStream(
                in,
                true,
                76,
                new byte[] {'\r', '\n'});

        in = new Base32InputStream(in, false);

        in.close();

        // VIOLATION.
        //
        // Also tests that:
        //
        // BaseNCodecInputStream+.new(InputStream, ..)
        //
        // correctly covers constructors having additional arguments.
        try {
            in.read();
        } catch (IOException expected) {
        }
    }

    @Test
    public void readByteArrayAfterClose() throws Exception {
        StrictInputStream in = new StrictInputStream();

        in.close();

        byte[] buffer = new byte[4];

        // VIOLATION: close -> read(byte[])
        try {
            in.read(buffer);
        } catch (IOException expected) {
        }
    }
}
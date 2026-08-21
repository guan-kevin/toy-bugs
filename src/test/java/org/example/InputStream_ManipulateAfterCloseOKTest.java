package org.example;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

import org.apache.commons.codec.binary.Base32InputStream;
import org.apache.commons.codec.binary.Base64InputStream;
import org.junit.Test;

public class InputStream_ManipulateAfterCloseOKTest {

    @Test
    public void ordinaryReadBeforeClose() throws Exception {
        StrictInputStream in = new StrictInputStream();

        // No close yet.
        in.read();
        in.available();
        in.skip(1);
        in.reset();

        // NO VIOLATION.
    }

    @Test
    public void closeWithoutManipulation() throws Exception {
        StrictInputStream in = new StrictInputStream();

        in.close();

        // NO VIOLATION.
    }

    @Test
    public void multipleClosesWithoutManipulation() throws Exception {
        StrictInputStream in = new StrictInputStream();

        in.close();
        in.close();
        in.close();

        // NO VIOLATION.
    }

    @Test
    public void byteArrayInputStreamReadAfterClose() throws Exception {
        ByteArrayInputStream in =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        in.close();

        // Explicitly excluded.
        // NO VIOLATION.
        in.read();
        in.available();
        in.skip(1);
        in.reset();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void stringBufferInputStreamReadAfterClose() throws Exception {
        StringBufferInputStream in =
                new StringBufferInputStream("hello");

        in.close();

        // Explicitly excluded.
        // NO VIOLATION.
        in.read();
        in.available();
        in.skip(1);
        in.reset();
    }

    @Test
    public void directBase32OverByteArray() throws Exception {
        ByteArrayInputStream root =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        Base32InputStream in =
                new Base32InputStream(root);

        in.close();

        // NO VIOLATION.
        in.read();
    }

    @Test
    public void directBase64OverByteArray() throws Exception {
        ByteArrayInputStream root =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        Base64InputStream in =
                new Base64InputStream(root);

        in.close();

        // NO VIOLATION.
        in.read();
    }

    @Test
    public void overloadedBase64ConstructorOverByteArray()
            throws Exception {

        ByteArrayInputStream root =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        Base64InputStream in =
                new Base64InputStream(
                        root,
                        true,
                        76,
                        new byte[] {'\r', '\n'});

        in.close();

        // Tests (InputStream, ..) matching.
        // NO VIOLATION.
        in.read();
    }

    @Test
    public void nestedBase64OverByteArray() throws Exception {
        InputStream in =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        in = new Base64InputStream(in);
        in = new Base64InputStream(in);
        in = new Base64InputStream(in);

        in.close();

        // NO VIOLATION.
        in.read();
    }

    @Test
    public void mixedNestedBaseNOverByteArray() throws Exception {
        InputStream in =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        in = new Base64InputStream(in);
        in = new Base32InputStream(in);
        in = new Base64InputStream(in);
        in = new Base32InputStream(in, false);

        in.close();

        // NO VIOLATION.
        in.read();
    }

    @Test
    public void deeplyNestedBaseNOverByteArray() throws Exception {
        InputStream in =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        for (int i = 0; i < 10; i++) {
            in = new Base32InputStream(
                    in,
                    true,
                    76,
                    new byte[] {'\r', '\n'});

            in = new Base32InputStream(in, false);
        }

        in.close();

        // NO VIOLATION.
        in.read();
    }

    /**
     * Tests:
     *
     * outer.close()
     *     -> inner.close()
     *
     * The inner close is below another close(), so your
     * !cflowbelow(call(* Closeable+.close()))
     * should suppress the inner close event.
     *
     * The later inner.read() therefore must NOT be reported as
     * "manipulate after close" by this specification.
     */
    @Test
    public void closeInsideCloseIsIgnored() throws Exception {
        final StrictInputStream inner =
                new StrictInputStream();

        InputStream outer = new InputStream() {

            @Override
            public int read() {
                return -1;
            }

            @Override
            public void close() throws IOException {
                inner.close();
            }
        };

        outer.close();

        // Java itself throws, because inner really was closed.
        //
        // But according to YOUR project-specific MOP repair,
        // inner.close() happened inside outer.close(), so the
        // close event was ignored.
        //
        // Therefore MOP should report NO VIOLATION here.
        try {
            inner.read();
        } catch (IOException expected) {
        }
    }

    /**
     * This is the stronger test for:
     *
     * !cflowbelow(call(* Closeable+.close()))
     *
     * on the MANIPULATE event.
     *
     * First close the stream normally, so MOP knows it is closed.
     * Then perform read() from inside another close().
     */
    @Test
    public void manipulateInsideCloseIsIgnored() throws Exception {
        final StrictInputStream alreadyClosed =
                new StrictInputStream();

        // Top-level close -- monitor sees this.
        alreadyClosed.close();

        Closeable outer = new Closeable() {
            @Override
            public void close() {
                try {
                    // This is a read-after-close at the Java level,
                    // but it occurs inside close().
                    alreadyClosed.read();
                } catch (IOException expected) {
                }
            }
        };

        // manipulate occurs under cflowbelow(close()).
        outer.close();

        // NO MOP VIOLATION.
    }

    /**
     * Ensures cflowbelow does not accidentally suppress a normal
     * read performed AFTER close() has returned.
     */
    @Test
    public void topLevelManipulateBeforeCloseIsFine() throws Exception {
        StrictInputStream in =
                new StrictInputStream();

        in.read();

        in.close();

        // Don't perform anything afterward.
        // NO VIOLATION.
    }
}
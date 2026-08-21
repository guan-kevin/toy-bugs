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
    public void directBase32OverByteArrayAllSupportedManipulations()
            throws Exception {

        ByteArrayInputStream root =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        Base32InputStream in =
                new Base32InputStream(root);

        in.close();

        // Safe Base-N stream.
        // NO VIOLATION.
        in.read();
        in.available();
        in.skip(1);
    }

    @Test
    public void directBase64OverByteArrayAllSupportedManipulations()
            throws Exception {

        ByteArrayInputStream root =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        Base64InputStream in =
                new Base64InputStream(root);

        in.close();

        // Safe Base-N stream.
        // NO VIOLATION.
        in.read();
        in.available();
        in.skip(1);
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
    public void overloadedBase32ConstructorOverByteArray()
            throws Exception {

        ByteArrayInputStream root =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        Base32InputStream in =
                new Base32InputStream(
                        root,
                        true,
                        76,
                        new byte[] {'\r', '\n'});

        in.close();

        // Tests BaseNCodecInputStream+.new(InputStream, ..)
        // for Base32 with additional constructor arguments.
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
    public void nestedMixedOverloadedBaseNOverByteArray()
            throws Exception {

        InputStream in =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        in = new Base64InputStream(
                in,
                true,
                76,
                new byte[] {'\r', '\n'});

        in = new Base32InputStream(in, false);

        in = new Base32InputStream(
                in,
                true,
                76,
                new byte[] {'\r', '\n'});

        in = new Base64InputStream(in);

        in.close();

        // The whole chain ultimately reaches ByteArrayInputStream.
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

    @Test
    public void nestedSafeBaseNManipulateBeforeAndAfterClose()
            throws Exception {

        InputStream in =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        in = new Base64InputStream(in);
        in = new Base32InputStream(in);
        in = new Base64InputStream(in);

        // Valid pre-close manipulations.
        in.read();
        in.available();
        in.skip(1);

        in.close();

        // The Base-N chain is safe because it ultimately reaches
        // ByteArrayInputStream.
        // NO VIOLATION.
        in.read();
        in.available();
        in.skip(1);
    }

    @Test
    public void everyLevelOfSafeBaseNChainShouldRemainExempt()
            throws Exception {

        ByteArrayInputStream root =
                new ByteArrayInputStream(
                        new byte[] {1, 2, 3, 4});

        Base64InputStream level1 =
                new Base64InputStream(root);

        Base32InputStream level2 =
                new Base32InputStream(level1);

        Base64InputStream level3 =
                new Base64InputStream(level2);

        /*
         * Closing level3 propagates:
         *
         * level3
         *   -> level2
         *      -> level1
         *         -> ByteArrayInputStream
         */
        level3.close();

        // Each Base-N object should already have been marked safe.
        // NO VIOLATION.
        level3.read();
        level2.read();
        level1.read();
    }

    /**
     * Tests:
     *
     * outer.close()
     *     -> inner.close()
     *
     * The inner close is below another close(), so
     *
     * !cflowbelow(call(* Closeable+.close()))
     *
     * should suppress the inner close event.
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

        // Java itself throws because inner really is closed.
        //
        // The monitor should NOT report it because inner.close()
        // occurred inside outer.close().
        try {
            inner.read();
        } catch (IOException expected) {
        }
    }

    /**
     * Tests a deeper close control flow:
     *
     * outer.close()
     *     -> middle.close()
     *          -> inner.close()
     *
     * Both nested closes should be ignored.
     */
    @Test
    public void deeplyNestedCloseInsideCloseIsIgnored()
            throws Exception {

        final StrictInputStream inner =
                new StrictInputStream();

        final InputStream middle = new InputStream() {

            @Override
            public int read() {
                return -1;
            }

            @Override
            public void close() throws IOException {
                inner.close();
            }
        };

        InputStream outer = new InputStream() {

            @Override
            public int read() {
                return -1;
            }

            @Override
            public void close() throws IOException {
                middle.close();
            }
        };

        outer.close();

        // inner is physically closed, but its close event happened
        // under the control flow of another close.
        //
        // NO MOP VIOLATION.
        try {
            inner.read();
        } catch (IOException expected) {
        }
    }

    /**
     * Tests:
     *
     * close()
     *     -> manipulate()
     *
     * where manipulate occurs inside another close().
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
                    // Java-level read-after-close, but this call occurs
                    // inside another close().
                    alreadyClosed.read();
                } catch (IOException expected) {
                }
            }
        };

        outer.close();

        // NO MOP VIOLATION.
    }

    @Test
    public void multipleManipulationsInsideCloseAreIgnored()
            throws Exception {

        final StrictInputStream alreadyClosed =
                new StrictInputStream();

        alreadyClosed.close();

        Closeable outer = new Closeable() {
            @Override
            public void close() {
                try {
                    alreadyClosed.read();
                } catch (IOException expected) {
                }

                try {
                    alreadyClosed.available();
                } catch (IOException expected) {
                }

                try {
                    alreadyClosed.skip(1);
                } catch (IOException expected) {
                }

                try {
                    alreadyClosed.reset();
                } catch (IOException expected) {
                }
            }
        };

        // All manipulations occur under close() control flow.
        // NO MOP VIOLATION.
        outer.close();
    }

    /**
     * Manipulation before close is valid.
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
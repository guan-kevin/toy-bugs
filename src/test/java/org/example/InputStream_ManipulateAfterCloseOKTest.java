package org.example;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base32InputStream;
import org.apache.commons.codec.binary.Base64InputStream;
import org.junit.Test;

public class InputStream_ManipulateAfterCloseOKTest {

    @Test
    public void byteArrayInputStreamAfterClose() throws Exception {
        ByteArrayInputStream in =
                new ByteArrayInputStream(new byte[] {1, 2, 3});

        in.close();

        // ByteArrayInputStream.close() has no effect.
        // No MOP violation should be reported.
        in.read();
    }

    @Test
    public void base64WithByteArrayInputStreamAfterClose() throws Exception {
        ByteArrayInputStream byteIn =
                new ByteArrayInputStream(new byte[] {1, 2, 3});

        Base64InputStream base64In =
                new Base64InputStream(byteIn);

        base64In.close();

        // Base64InputStream ultimately wraps ByteArrayInputStream.
        // No MOP violation should be reported.
        base64In.read();
    }

    @Test
    public void base32WithByteArrayInputStreamAfterClose() throws Exception {
        ByteArrayInputStream byteIn =
                new ByteArrayInputStream(new byte[] {1, 2, 3});

        Base32InputStream base32In =
                new Base32InputStream(byteIn);

        base32In.close();

        // No MOP violation should be reported.
        base32In.read();
    }

    @Test
    public void nestedBase32Base64WithByteArrayInputStreamAfterClose()
            throws Exception {

        ByteArrayInputStream byteIn =
                new ByteArrayInputStream(new byte[] {1, 2, 3});

        Base64InputStream base64In =
                new Base64InputStream(byteIn);

        Base32InputStream base32In =
                new Base32InputStream(base64In, false);

        base32In.close();

        // Chain:
        //
        // Base32InputStream
        //        |
        // Base64InputStream
        //        |
        // ByteArrayInputStream
        //
        // No MOP violation should be reported.
        base32In.read();
    }

    @Test
    public void deeplyNestedBaseStreamsAfterClose() throws Exception {
        InputStream in =
                new ByteArrayInputStream(new byte[] {1, 2, 3});

        in = new Base64InputStream(in);
        in = new Base32InputStream(in, false);
        in = new Base64InputStream(in);
        in = new Base32InputStream(in, true, 76,
                new byte[] {'\r', '\n'});

        in.close();

        // All BaseNCodecInputStreams ultimately wrap
        // the original ByteArrayInputStream.
        //
        // No MOP violation should be reported.
        in.read();
    }

    @Test
    public void manipulateBeforeClose() throws Exception {
        InputStream in =
                new Base64InputStream(
                        new ByteArrayInputStream(
                                new byte[] {1, 2, 3}));

        // Manipulation before close is always valid.
        in.read();

        in.close();
    }

    @Test
    public void multipleManipulationsBeforeClose() throws Exception {
        InputStream in =
                new Base32InputStream(
                        new ByteArrayInputStream(
                                new byte[] {1, 2, 3, 4}));

        in.read();
        in.available();
        in.skip(1);

        in.close();

        // Still safe because the chain ends in ByteArrayInputStream.
        in.read();
        in.available();
        in.skip(1);
    }
}
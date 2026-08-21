package org.apache.commons.codec.binary;

import java.io.InputStream;

public class Base32InputStream extends BaseNCodecInputStream {

    public Base32InputStream(InputStream in) {
        super(in);
    }

    public Base32InputStream(InputStream in, boolean encode) {
        super(in);
    }

    public Base32InputStream(
            InputStream in,
            boolean encode,
            int lineLength,
            byte[] lineSeparator) {

        super(in);
    }
}
package org.apache.commons.codec.binary;

import java.io.InputStream;

public class Base64InputStream extends BaseNCodecInputStream {

    public Base64InputStream(InputStream in) {
        super(in);
    }

    public Base64InputStream(InputStream in, boolean encode) {
        super(in);
    }

    public Base64InputStream(
            InputStream in,
            boolean encode,
            int lineLength,
            byte[] lineSeparator) {

        super(in);
    }
}
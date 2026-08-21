package org.apache.commons.codec.binary;

import java.io.FilterInputStream;
import java.io.InputStream;

public class BaseNCodecInputStream extends FilterInputStream {

    protected BaseNCodecInputStream(InputStream in) {
        super(in);
    }
}
package org.example;
import java.io.IOException;
import java.io.InputStream;

public class StrictInputStream extends InputStream {

    private final byte[] data = {1, 2, 3, 4};
    private int position;
    private boolean closed;

    @Override
    public int read() throws IOException {
        checkClosed();

        if (position >= data.length) {
            return -1;
        }

        return data[position++] & 0xff;
    }

    @Override
    public int available() throws IOException {
        checkClosed();
        return data.length - position;
    }

    @Override
    public long skip(long n) throws IOException {
        checkClosed();

        long skipped = Math.min(n, data.length - position);
        position += (int) skipped;
        return skipped;
    }

    @Override
    public synchronized void reset() throws IOException {
        checkClosed();
        position = 0;
    }

    @Override
    public void close() {
        closed = true;
    }

    private void checkClosed() throws IOException {
        if (closed) {
            throw new IOException("Stream is closed");
        }
    }
}

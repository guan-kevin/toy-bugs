package org.example;

import org.junit.Test;

import java.net.Socket;
import java.net.SocketException;

public class Socket_TimeoutTest {
    @Test public void testTimeout() throws SocketException {
        Socket socket = new Socket();
        socket.setSoTimeout(-1);
    }
}

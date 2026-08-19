package org.example;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocket_BacklogTest {
    @Test public void testBacklog() throws IOException {
        ServerSocket obj = new ServerSocket(-1);
    }
}

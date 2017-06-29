package com.ak.learning.concurrency.blockingserver;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 *

 */
public class SocketOverload {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket[] sockets = new Socket[8000];
        for (int i = 0; i < sockets.length; i++) {
            sockets[i] = new Socket("localhost", 9092);
        }
        System.out.println("Done!");

        TimeUnit.SECONDS.sleep(100000);
    }
}

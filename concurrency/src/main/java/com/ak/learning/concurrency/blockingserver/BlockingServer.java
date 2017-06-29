package com.ak.learning.concurrency.blockingserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9090);
        while (true) {
            Socket s = ss.accept();
            handle(s);
        }
    }

    private static void handle(Socket socket) throws IOException {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {
            int data;
            while ((data = in.read()) != -1) {
                data = translate(data);
                out.write(data);
            }
        }
    }

    private static int translate(int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;
    }
}

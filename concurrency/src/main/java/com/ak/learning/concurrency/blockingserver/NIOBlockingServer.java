package com.ak.learning.concurrency.blockingserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class NIOBlockingServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress("localhost",9093));
        while (true) {
            SocketChannel s = ss.accept();
            System.out.println("accepting connection from: " + s);
            handle(s);
        }
    }

    private static void handle(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(80);
        try {
            int data;
            while ((data = socketChannel.read(buffer)) != -1) {
                buffer.flip();
                translate(buffer);
                // you can't write into the socket channel from the buffer like so
                //socketChannel.write(buffer);
                while (buffer.hasRemaining()) {
                    socketChannel.write(buffer);
                }
                buffer.compact();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void translate(ByteBuffer buffer) {
        for (int i = 0; i < buffer.limit(); i++) {
            buffer.put(i, (byte) translate(buffer.get(i)));
        }
    }

    private static int translate(int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;
    }
}

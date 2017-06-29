package com.ak.learning.concurrency.blockingserver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

// REALLY BAD WAY OF DOING NON-BLOCKING. DONT DO THIS
public class PollingNIOBlockingServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.configureBlocking(false);
        ss.bind(new InetSocketAddress("localhost",9094));
        Map<SocketChannel, ByteBuffer> sockets = new ConcurrentHashMap<>();
        while (true) {
            SocketChannel s = ss.accept(); // almost always returns a null
            if (s != null) {
                s.configureBlocking(false);
                System.out.println("accepting connection from: " + s);
                sockets.put(s, ByteBuffer.allocateDirect(80));
            }
            sockets.keySet().removeIf(new Predicate<SocketChannel>() {
                @Override
                public boolean test(SocketChannel socketChannel) {
                    return !socketChannel.isOpen();
                }
            });

            sockets.forEach(PollingNIOBlockingServer::handle);
        }
    }

    private static void handle(SocketChannel sock, ByteBuffer buf) {
        try {
            int data = sock.read(buf);
            if (data == -1) {
                close(sock);
            } else if (data == 0) {
                buf.flip();
                translate(buf);
                while (buf.hasRemaining()) {
                    sock.write(buf);
                }
                buf.compact();
            }
        } catch (IOException e) {
            close(sock);
            throw new UncheckedIOException(e);
        }
    }

    private static void close(SocketChannel sock) {
        try {
            sock.close();
        } catch (IOException e) {
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

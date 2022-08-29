package com.ak.learning.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectNonDirectByteBuffer {
    public static void main(String[] args) throws IOException {
        String fileName = DirectNonDirectByteBuffer.class.getResource("/sherlock.txt").getFile();
        DirectNonDirectByteBuffer ddb = new DirectNonDirectByteBuffer();
        System.out.println("--------------- USING DIRECT BYTE BUFFER ----------------");
        long startTime = System.currentTimeMillis();
        ddb.readWithDirectBuffer(fileName);
        System.out.println("time taken: " + (System.currentTimeMillis() - startTime) + " milli seconds");

        System.out.println("--------------- USING NON-DIRECT BYTE BUFFER ----------------");
        startTime = System.currentTimeMillis();
        ddb.readWithNonDirectBuffer(fileName);
        System.out.println("time taken: " + (System.currentTimeMillis() - startTime) + " milli seconds");

    }

    private void readWithDirectBuffer(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        FileChannel channel = FileChannel.open(path);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10); // 10KB
        System.out.println("Is direct buffer: " + buffer.isDirect());
        System.out.println("Has backing array: " + buffer.hasArray());
        System.out.println("Reading file...");

        int noOfBytes = -1;

        while ((noOfBytes = channel.read(buffer)) != -1) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            //System.out.println(new String(bytes));
            buffer.clear();
        }

//        for (int i = 0; i < 25; i++) {
//            while (noOfBytes != -1) {
//                buffer.clear();
//                noOfBytes = channel.read(buffer);
//            }
//            buffer.clear();
//            channel.position(0);
//            noOfBytes = channel.read(buffer);
//        }
        channel.close();
        System.out.println("---- DONE -----");
    }

    private void readWithNonDirectBuffer(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        FileChannel channel = FileChannel.open(path);
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 10); // 10KB
        System.out.println("Is direct buffer: " + buffer.isDirect());
        System.out.println("Has backing array: " + buffer.hasArray());
        System.out.println("Reading file...");

        int noOfBytes = -1;

        while ((noOfBytes = channel.read(buffer)) != -1) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            //System.out.println(new String(bytes));
            buffer.clear();
        }
//        int noOfBytes = channel.read(buffer);
//
//        for (int i = 0; i < 25; i++) {
//            while (noOfBytes != -1) {
//                buffer.clear();
//                noOfBytes = channel.read(buffer);
//            }
//            buffer.clear();
//            channel.position(0);
//            noOfBytes = channel.read(buffer);
//        }
        channel.close();
        System.out.println("---- DONE -----");
    }
}

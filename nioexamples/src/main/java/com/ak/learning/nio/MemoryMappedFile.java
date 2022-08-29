package com.ak.learning.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/*
 * Memory mapped files are special files in Java which allows Java program to access contents directly from
 * memory. This is achieved by mapping whole file or portion of file into memory and operating system takes
 * care of loading page requested and writing into file while application only deals with memory which results
 * in very fast IO operations. Memory used to load Memory mapped file is outside of Java heap Space and reside
 * on shared memory which allow two different process to access File. (By the way this depends upon, whether
 * you are using direct or non-direct byte buffer.
 *
 * There are 3 types of ByteBuffers: Direct, Non-Direct and MappedByteBuffer
 * You can create both direct and non-direct buffers using java.nio.ByteBuffer class, while MappedByteBuffer
 * is a subclass of ByteBuffer, which is created by FileChannel.map() method, to operate on memory mapped file.
 * The main difference between direct and non-direct byte buffers are there memory location, non-direct byte
 * buffers are just a wrapper around byte array and they reside in Java Heap memory while direct byte buffer is
 * outside of JVM and memory is not allocated from the heap. In the case of Direct byte buffer, JVM performs
 * native IO operation directly into the buffer, without copying them into any intermediate buffer, this makes
 * it very attractive for performing high-speed IO operation on them, but this facility comes with care.
 * If a memory mapped file is shared between multiple processes then you need to ensure that it won't get
 * corrupted i.e. some regions of memory mapped file not becoming unavailable
 *
 * One more difference between direct and non-direct byte buffers are that former's memory footprint may not be
 * obvious because they are allocated outside of Java heap while non-direct buffers consume heap space and are
 * subject to garbage collection.
 *
 * Java supports memory mapped file with java.nio package and has MappedByteBuffer to read and write from memory.
 * Key advantage of  Memory Mapped File is that OS takes care of reading and  writing and even if your program
 * crashed just after writing into memory. OS will take care of writing content to File.
 * Another advantage is shared memory. Memory mapped files can be accessed by more than one process
 * and can be act as shared memory with extremely low latency.
 *
 * Downside of the memory mapped file is page fault. Since it reads it from the memory, if the requested page
 * is not present in the memory, it can result in a page fault.
 */
public class MemoryMappedFile {
    private static long count = 10485760; // 10 MB (1 MB = 1 million bytes i.e. 10^6 in decimal and 1,048,576 in binary)

    public static void main(String[] args) throws IOException {
        RandomAccessFile memoryMappedFile = new RandomAccessFile("/tmp/largeFile.txt", "rw");
        FileChannel channel = memoryMappedFile.getChannel();
        MappedByteBuffer out = channel.map(FileChannel.MapMode.READ_WRITE, 0, count); // This a direct byte buffer

        // write the file
        for (int i = 0; i < count; i++) {
            out.put((byte)'S');
        }
        System.out.println("Wrote the file /tmp/largeFile.txt completed");

        // reading the same file from memory... reading just a sample for POC.
        for (int i = 0; i < 10; i++) {
            System.out.println((char)out.get(i));
        }
        System.out.println("Reading the /tmp/largeFile.txt completed");
    }
}

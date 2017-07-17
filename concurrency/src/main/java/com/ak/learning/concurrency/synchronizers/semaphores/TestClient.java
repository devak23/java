package com.ak.learning.concurrency.synchronizers.semaphores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestClient {
    private static String[] documents = new String[] {
        "Absolute Java (5th ed.) [Savitch & Mock 2012-03-19].pdf"
            , "Addison.Wesley.Java.Concurrency.in.Practice.May.2006.pdf"
            , "[Addison-Wesley] - Java.An.Introduction to Problem Solving and Programming, 6th ed. - [Savitch].pdf"
            , "Concurrent and Distributed Computing in Java [Garg 2004-02-04].pdf"
            ,"Core Java Volume I--Fundamentals, 10th Edition.pdf"
            ,"Core_Java_2C_Volume_II--Advanced_Features_2_10th_Edition.pdf"
            ,"Effective Java, 2nd Edition.pdf"
            ,"FileChannel and ByteBuffer in Java.pdf"
            ,"G1_ Java's Garbage First Garbage Collector _ Dr Dobb's.pdf"
            ,"GC.pdf"
            ,"Introduction to Programming in Java_ A Problem Solving Approach [Dean & Dean 2008-03-29].pdf"
            ,"Java 7 Concurrency Cookbook.pdf"
    };

    public static void main(String[] args) {
        PrintQueue<String> printQueue = new PrintQueue<>();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.submit(new Job<>(printQueue, documents[i]));
        }
        pool.shutdown();
    }
}

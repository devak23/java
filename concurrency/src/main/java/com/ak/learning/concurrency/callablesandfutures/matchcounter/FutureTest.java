package com.ak.learning.concurrency.callablesandfutures.matchcounter;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTest {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Enter a base directory (ex: /usr/local/jdk5.0/src: ");
            String directory = in.nextLine();
            System.out.println("Enter a key word (ex: volatile): ");
            String keyword = in.nextLine();

            MatchCounter counter = new MatchCounter(new File(directory), keyword);
            FutureTask<Integer> task = new FutureTask<>(counter);
            Thread worker = new Thread(task);
            worker.start();
            try {
                System.out.println(task.get() + " matching files.");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}

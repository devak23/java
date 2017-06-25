package com.ak.learning.concurrency.callablesandfutures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Counts the file and subdirectories that contain the given keyword
 */
public class MatchCounter implements Callable<Integer> {
    private File directory;
    private String keyword;

    public MatchCounter(File file, String keyword) {
        this.directory = file;
        this.keyword = keyword;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        try {
            File[] files = directory.listFiles();
            if (files == null || files.length == 0) {
                return 0;
            }
            List<Future<Integer>> results = new ArrayList<>();
            for (File f : files) {
                if (f.isDirectory()) {
                    MatchCounter mc = new MatchCounter(f, keyword);
                    FutureTask<Integer> task = new FutureTask<>(mc);
                    results.add(task);
                    Thread t = new Thread(task);
                    t.start();
                } else {
                    if (search(f)) {
                        count++;
                    }
                }
            }

            for (Future<Integer> result : results) {
                try {
                    count += result.get();
                } catch (ExecutionException ee) {
                    ee.printStackTrace();
                }
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return count;
    }

    /**
     * Searches the file for a given keyword
     */
    private boolean search(File file) {
        try (Scanner in = new Scanner(file)) {
            boolean found = false;
            while (!found && in.hasNextLine()) {
                String line = in.nextLine();
                found = line.contains(keyword);
            }
            return found;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}

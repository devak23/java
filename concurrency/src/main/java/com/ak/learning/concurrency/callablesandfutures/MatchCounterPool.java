package com.ak.learning.concurrency.callablesandfutures;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MatchCounterPool implements Callable<Integer> {
    private File directory;
    private String keyword;
    private ExecutorService pool;

    public MatchCounterPool(File file, String keyword, ExecutorService pool) {
        this.directory = file;
        this.keyword = keyword;
        this.pool = pool;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        File[] files = directory.listFiles();
        if (files == null) {
            return 0;
        }
        List<Future<Integer>> results = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) {
                MatchCounterPool mcp = new MatchCounterPool(file, keyword, pool);
                Future<Integer> result = pool.submit(mcp);
                results.add(result);
            } else {
                if (search(file)) {
                    count++;
                }
            }
        }

        for (Future<Integer> future : results) {
            count += future.get();
        }
        return count;
    }

    private boolean search(File file) {
        if (file == null || !file.exists()) {
            return false;
        }

        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.contains(keyword)) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

package com.ak.learning.concurrency.callablesandfutures.matchcounter;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.*;

public class FuturePoolTest {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Enter the base directory to look for: ");
            String directory = in.nextLine();
            System.out.println("Enter the keyword to search: ");
            String keyword = in.nextLine();
            ExecutorService pool = Executors.newCachedThreadPool();
            MatchCounterPool mcp = new MatchCounterPool(new File(directory), keyword, pool);
            Future<Integer> result = pool.submit(mcp);
            System.out.println(result.get() + " occurrences have been found.");
            pool.shutdown();

            int largestPoolSize = ((ThreadPoolExecutor)pool).getLargestPoolSize();
            System.out.println("Largest Pool size = " + largestPoolSize);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

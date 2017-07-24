package com.ak.learning.concurrency.callablesandfutures.urlfetcher;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UrlFetcher {
    private String[] urls;

    public UrlFetcher(String[] urls) {
        this.urls = urls;
    }

    public UrlObject[] goFetch() {
        UrlObject[] content = new UrlObject[urls.length];
        ExecutorService pool = Executors.newFixedThreadPool(5);
        int i = 0;
        for (String url : urls) {
            try {
                Future<UrlObject> result = pool.submit(new ContentFetcher(url));
                content[i++] = result.get();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        }
        pool.shutdown();
        return content;
    }
}

package com.ak.learning.concurrency.core;

import java.util.concurrent.ThreadFactory;

public class SimpleThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable runnable) {
        // return new Thread(r)
        return Thread.ofVirtual().name("STF-",0).unstarted(runnable);
    }
}

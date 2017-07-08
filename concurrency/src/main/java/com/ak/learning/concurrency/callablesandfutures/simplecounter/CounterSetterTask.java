package com.ak.learning.concurrency.callablesandfutures.simplecounter;

public class CounterSetterTask implements Runnable {
    private SimpleCounter counter;

    public CounterSetterTask(SimpleCounter c) {
        this.counter = c;
    }

    @Override
    public void run() {
        while (true) {
            counter.setValue(100);
        }
    }
}

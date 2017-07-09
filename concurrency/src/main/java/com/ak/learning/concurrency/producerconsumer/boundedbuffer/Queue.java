package com.ak.learning.concurrency.producerconsumer.boundedbuffer;

public interface Queue<T> {
    boolean put(T object);
    T take();
    int count();
}

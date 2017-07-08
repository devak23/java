package com.ak.learning.concurrency.producerconsumer.threadsafestack;

public interface IStack<T> {
    void push(T athing);
    T pop();
    T peek();
    int count();
}

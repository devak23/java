package com.ak.learning.concurrency.producerconsumer.threadsafestack;

import java.util.LinkedList;
import java.util.List;

public class ThreadSafeStack<T> implements IStack<T> {
    private List<T> listOfThings;

    public ThreadSafeStack() {
        listOfThings = new LinkedList<>();
    }

    @Override
    public void push(T athing) {
        synchronized (listOfThings) {
            listOfThings.add(athing);
        }
    }

    @Override
    public T pop() {
        synchronized (listOfThings) {
            if (listOfThings.isEmpty()) {
                return null;
            }
            return listOfThings.remove(listOfThings.size());
        }
    }

    @Override
    public T peek() {
        synchronized (listOfThings) {
            if (listOfThings.isEmpty()) {
                return null;
            }
            return listOfThings.get(listOfThings.size());
        }
    }

    @Override
    public int count() {
        synchronized (listOfThings) {
            return listOfThings.size();
        }
    }
}

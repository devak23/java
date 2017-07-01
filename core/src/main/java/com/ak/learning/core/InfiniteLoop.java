package com.ak.learning.core;

public class InfiniteLoop {

    // This should ideally be an infinite loop
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i+=2) {
            System.out.println(i);
        }
    }
}

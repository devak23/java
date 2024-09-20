package com.ak.learning.concurrency.virtualthreads;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}

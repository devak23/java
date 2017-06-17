package com.ak.learning;

// A test bean to be loaded by MyClassLoader
public class Greeter {
    public Greeter() {
    }

    public String getMessage(String message) {
        return "Greeter says: " + message;
    }
}

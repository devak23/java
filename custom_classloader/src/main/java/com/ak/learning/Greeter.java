package com.ak.learning;

// A test bean to be loaded by MyClassLoader
public class Greeter {
    private String message;

    public Greeter() {
    }

    public String getMessage() {
        return "Greeter says: " + message;
    }

    public Greeter setMessage(String message) {
        this.message = message;
        return this;
    }
}

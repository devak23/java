package com.ak.learning.patterns.singleton;


public class TestClient {

    public static void main(String... args) {
        Elvis elvis = Elvis.INSTANCE;
        elvis.houndDog();
    }
}

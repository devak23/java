package com.ak.learning.patterns.builder;


public class EngineBuilder implements Builder {


    public void buildPart(Car car) {
        System.out.println("Adding engine to the car");
        car.addPart("Engine");
    }

    public String getName() {
        return "Engine Builder";
    }
}

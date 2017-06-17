package com.ak.learning.patterns.builder;


public class WheelBuilder implements Builder {
    @Override
    public void buildPart(Car car) {
        System.out.println("Adding wheels to the car");
        car.addPart("Wheels");
    }

    @Override
    public String getName() {
        return "Wheel Builder";
    }
}

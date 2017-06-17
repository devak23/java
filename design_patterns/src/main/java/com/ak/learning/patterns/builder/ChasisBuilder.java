package com.ak.learning.patterns.builder;

/**
 * Created by abhay on 21/5/17.
 */
public class ChasisBuilder implements Builder {
    @Override
    public void buildPart(Car car) {
        System.out.println("Adding chasis to the car");
        car.addPart("Chasis");
    }

    @Override
    public String getName() {
        return "Chasis Builder";
    }
}

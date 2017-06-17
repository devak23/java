package com.ak.learning.patterns.builder;

/**
 * Created by abhay on 21/5/17.
 */
public class CarSkeletonBuilder implements Builder {
    @Override
    public void buildPart(Car car) {
        System.out.println("Adding skeleton to the car");
        car.addPart("Car skeleton");
    }

    @Override
    public String getName() {
        return "CarSkeleton builder";
    }
}

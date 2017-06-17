package com.ak.learning.patterns.builder;

import java.util.LinkedHashSet;
import java.util.Set;

// The director
public class CarAssembly {
    // has a list of builders
    private Set<Builder> builders;

    public CarAssembly() {
        builders = new LinkedHashSet<>();
    }

    public void addBuilder(Builder builder) {
        this.builders.add(builder);
    }

    public void removeBuilder(Builder builder) {
        this.builders.remove(builder);
    }

    public Car buildCar() {
        Car car = new Car();

        // build the parts of the car via the builders
        for (Builder builder : builders) {
            builder.buildPart(car);
        }
        return car;
    }
}

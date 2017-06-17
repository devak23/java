package com.ak.learning.patterns.strategy;

public class Customer {
    private String name;
    private int points;

    public Customer(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public int getPoints() {
        return points;
    }

    public Customer setPoints(int points) {
        this.points = points;
        return this;
    }
}

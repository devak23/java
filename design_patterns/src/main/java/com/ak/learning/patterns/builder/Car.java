package com.ak.learning.patterns.builder;

import java.util.ArrayList;
import java.util.List;


public class Car {

    private List<String> parts;

    public Car() {
        parts = new ArrayList<String>();
    }

    public void addPart(String part) {
        this.parts.add(part);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("The car has following parts: ");
        parts.forEach(part -> builder.append(part).append(", "));
        builder.deleteCharAt(builder.length()-2);
        return builder.toString();
    }
}

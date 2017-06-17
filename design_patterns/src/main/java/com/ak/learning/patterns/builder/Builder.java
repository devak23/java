package com.ak.learning.patterns.builder;


public interface Builder {
    void buildPart(Car car);
    String getName();
}

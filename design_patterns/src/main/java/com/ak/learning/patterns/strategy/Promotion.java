package com.ak.learning.patterns.strategy;

public interface Promotion {
    String getName();
    double getDiscount(Order order);
}

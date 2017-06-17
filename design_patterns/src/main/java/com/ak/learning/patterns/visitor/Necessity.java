package com.ak.learning.patterns.visitor;


public class Necessity extends Item {
    public Necessity(String name, double price) {
        super(name, price);
    }


    @Override
    public double accept(Visitor visitor) {
        return visitor.visit(this);
    }
}

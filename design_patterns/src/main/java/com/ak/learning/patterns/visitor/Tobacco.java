package com.ak.learning.patterns.visitor;


public class Tobacco extends Item {
    public Tobacco(String name, double price) {
        super(name, price);
    }


    @Override
    public double accept(Visitor visitor) {
        return visitor.visit(this);
    }
}

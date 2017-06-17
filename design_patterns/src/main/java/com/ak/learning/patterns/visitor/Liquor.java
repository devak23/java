package com.ak.learning.patterns.visitor;


public class Liquor extends Item {
    public Liquor(String name, double price) {
        super(name, price);
    }


    @Override
    public double accept(Visitor visitor) {
        return visitor.visit(this);
    }
}

package com.ak.learning.patterns.visitor;

public class TaxVisitor implements Visitor {
    @Override
    public double visit(Liquor liquor) {
        return liquor.getPrice() * 0.34 + liquor.getPrice();
    }

    @Override
    public double visit(Tobacco tobacco) {
        return tobacco.getPrice() * 0.49 + tobacco.getPrice();
    }

    @Override
    public double visit(Necessity necessity) {
        return necessity.getPrice() * 0 + necessity.getPrice();
    }
}

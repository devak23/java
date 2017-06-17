package com.ak.learning.patterns.visitor;


public interface Visitor {
    double visit(Liquor liquor);
    double visit(Tobacco tobacco);
    double visit(Necessity necessity);
}

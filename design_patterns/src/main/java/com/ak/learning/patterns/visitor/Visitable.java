package com.ak.learning.patterns.visitor;


public interface Visitable {

    double accept(Visitor vistor);
}

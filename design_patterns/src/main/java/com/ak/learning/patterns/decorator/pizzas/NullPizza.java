package com.ak.learning.patterns.decorator.pizzas;

import com.ak.learning.patterns.decorator.PizzaType;

public final class NullPizza extends Pizza {
    private boolean valid;

    public boolean isValid() {
        return Boolean.FALSE;
    }

    @Override
    public PizzaType getType() {
        throw new RuntimeException("Illegal invocation on a Null Pizza. Please check the method isValid() first");
    }
}

package com.ak.learning.patterns.decorator.pizzas;

import com.ak.learning.patterns.decorator.PizzaType;

public class VegSupremePizza extends Pizza {
    @Override
    public PizzaType getType() {
        return PizzaType.VEG_SUPREME;
    }
}

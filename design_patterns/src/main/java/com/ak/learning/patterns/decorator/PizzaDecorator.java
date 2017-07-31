package com.ak.learning.patterns.decorator;

import com.ak.learning.patterns.decorator.pizzas.Pizza;

public enum PizzaDecorator {
    INSTANCE;

    public PizzaDecorator add(Pizza pizza, Condiment condiment) {
        pizza.addCondiment(condiment);
        return this;
    }
}

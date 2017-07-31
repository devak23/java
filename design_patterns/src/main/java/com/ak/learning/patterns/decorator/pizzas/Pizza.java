package com.ak.learning.patterns.decorator.pizzas;

import com.ak.learning.patterns.decorator.Condiment;
import com.ak.learning.patterns.decorator.PizzaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class Pizza {
    protected Collection<String> contents;

    public Pizza() {
        contents = new ArrayList<>(
                Arrays.asList("Pizza bread", "Tomato sauce", "Cheese")
        );
    }

    public String toString() {
        if (isValid()) {
            return "Pizza Type: " + getType() + "; Contents: " + contents.stream().collect(Collectors.joining(","));
        } else {
            return "Invalid Pizza specified or no implementation class found";
        }
    }

    /**
     * Returns if the factory can make a valid pizza given it's type. If the factory cannot,
     * then it retutns a <code>NullPizza</code> which overrides this method to return FALSE
     * @return an indicator if the pizza is valid or not.
     */
    public boolean isValid() {
        return Boolean.TRUE;
    }

    public abstract PizzaType getType();

    public void addCondiment(Condiment condiment) {
        this.contents.add(condiment.getText());
    }
}

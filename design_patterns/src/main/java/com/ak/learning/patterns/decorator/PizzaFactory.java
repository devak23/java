package com.ak.learning.patterns.decorator;

import com.ak.learning.patterns.decorator.pizzas.NullPizza;
import com.ak.learning.patterns.decorator.pizzas.Pizza;

public enum PizzaFactory {
    INSTANCE;

    public Pizza makePizza(PizzaType pizzaType) {
        for (PizzaType type: PizzaType.values()) {
            if (type.equals(pizzaType)) {
                try {
                    Class<? extends Pizza> pizzaClass = (Class<? extends Pizza>) Class.forName(pizzaType.getClazz());
                    return pizzaClass.newInstance();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    return new NullPizza();
                }
            }
        }

        return new NullPizza();
    }
}

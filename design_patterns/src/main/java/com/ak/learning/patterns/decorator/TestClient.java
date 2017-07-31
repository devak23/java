package com.ak.learning.patterns.decorator;

import com.ak.learning.patterns.decorator.pizzas.Pizza;

public class TestClient {
    public static void main(String[] args) {
        Pizza pizza = PizzaFactory.INSTANCE.makePizza(PizzaType.VEG_SUPREME);

        PizzaDecorator decorator = PizzaDecorator.INSTANCE;
        decorator
                .add(pizza, Condiments.OLIVES)
                .add(pizza, Condiments.JALEPANO)
                .add(pizza, Condiments.PINEAPPLE)
                .add(pizza, Condiments.MUSHROOM)
                .add(pizza, Condiments.EXTRA_CHEESE);

        System.out.println(pizza);
    }
}

package com.ak.learning.patterns.decorator;

import com.ak.learning.patterns.decorator.pizzas.Pizza;

public class TestClient {
    public static void main(String[] args) {
        Pizza pizza = PizzaFactory.INSTANCE.makePizza(PizzaType.VEG_SUPREME);

        PizzaDecorator decorator = PizzaDecorator.INSTANCE;
        decorator
                .add(pizza, Condiment.OLIVES)
                .add(pizza, Condiment.JALEPANO)
                .add(pizza, Condiment.PINEAPPLE)
                .add(pizza, Condiment.MUSHROOM)
                .add(pizza, Condiment.EXTRA_CHEESE);

        System.out.println(pizza);
    }
}

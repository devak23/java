package com.ak.learning.patterns.decorator;

public enum Condiments {
    TOMATOES ("Red tomatoes"), EXTRA_TOMATOES("Extra tomatoes")
    , ONION ("Onion"), EXTRA_ONIONS ("Extra Onion")
    , OLIVES ("Black Olives"), EXTRA_OLIVES ("Extra Olives")
    , JALEPANO ("Jalepano"), EXTRA_JALEPANO ("Extra Jalepano")
    , PINEAPPLE ("Fresh Pineapple"), EXTRA_PINEAPPLE ("Extra Pineapple")
    , MUSHROOM ("Shiitake Mushroom"), EXTRA_MUSHROOM ("Extra Mushroom")
    , CHEESE("Cheese"), EXTRA_CHEESE("Extra Cheese");


    private String text;
    Condiments(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

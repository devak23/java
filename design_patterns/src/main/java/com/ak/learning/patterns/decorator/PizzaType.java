package com.ak.learning.patterns.decorator;

public enum PizzaType {
    MARGARITA ("com.ak.learning.patterns.decorator.pizzas.MargaritaPizza")
    , VEG_SUPREME ("com.ak.learning.patterns.decorator.pizzas.VegSupremePizza")
    , COUNTRY_SPECIAL ("com.ak.learning.patterns.decorator.pizzas.CountrySpecialPizza")
    , PEPPY_PANEER ("com.ak.learning.patterns.decorator.pizzas.PeppyPaneerPizza")
    , FARM_HOUSE ("com.ak.learning.patterns.decorator.pizzas.FarmHousePizza")
    , MEXICAN_GREENWAVE ("com.ak.learning.patterns.decorator.pizzas.MexicanGreenWavePizza");

    private String clazz;

    PizzaType(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return this.clazz;
    }
}

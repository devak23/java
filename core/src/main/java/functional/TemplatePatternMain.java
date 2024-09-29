package functional;

import functional.model.Pizza;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class TemplatePatternMain {

    public static void main(String[] args) {

        PizzaMaker pizzaMaker = new PizzaMaker();

        Pizza sicilianPizza = new Pizza("Sicilian");
        pizzaMaker.make(sicilianPizza, (Pizza p) -> {
            log.info("Adding toppings: tomato, onion, anchovies and herbs to {} pizza", p.getName());
        });


        Pizza hawaiianPizza = new Pizza("Hawaiian");
        pizzaMaker.make(hawaiianPizza, (Pizza p) -> {
            log.info("Adding toppings: cheese, tomato, onion, pineapple, paprika to {} pizza", p.getName());
        });

    }


    static class PizzaMaker {
        public void make(Pizza pizza, Consumer<Pizza> addTopIngridients) {
            makeDough(pizza);
            addTopIngridients.accept(pizza);
            bake(pizza);
        }

        private void makeDough(Pizza pizza) {
            log.info("Making the dough for {} pizza.", pizza.getName());

        }

        private void bake(Pizza pizza) {
            log.info("Baking the {} pizza.", pizza.getName());
        }
    }
}

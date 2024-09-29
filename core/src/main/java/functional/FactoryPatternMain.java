package functional;

import functional.model.Gac;
import functional.model.Melon;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FactoryPatternMain {

    public static void main(String[] args) {

        Gac gac = (Gac) MelonFactory.newInstance(Gac.class);
        log.info("fruit = {}", gac.getName());

        Melon watermelon = (Melon) MelonFactory.newInstance("Watermelon", 2.4f, "Red");
        Melon cantaloupe = (Melon) MelonFactory.newInstance("Cantaloupe", 1.3f, "Yellow");

        log.info("cantaloupe = {}", cantaloupe);
        log.info("watermelon = {}", watermelon);
    }
}

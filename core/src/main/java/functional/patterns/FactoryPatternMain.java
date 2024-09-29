package functional.patterns;

import functional.model.*;
import functional.spec.TriFunction;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Supplier;

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


    static class MelonFactory {

        private MelonFactory() {}


        private static final Map<String, Supplier<Fruit>> MELONS = Map.of(
                "Gac", Gac::new
                , "Hemi", Hemi::new
                , "Cantaloupe", Cantaloupe::new
        );


        public static Fruit newInstance(Class<?> clazz) {
            Supplier<Fruit> supplier = MELONS.get(clazz.getSimpleName());

            if (supplier == null) {
                throw new IllegalArgumentException(STR."Unknown fruit type: \{clazz.getSimpleName()}");
            }

            return supplier.get();
        }

        private static final TriFunction<String, Float, String, Melon> MELON = Melon::new;


        public static Fruit newInstance(String name, float weight, String color) {
            return MELON.apply(name, weight, color);
        }

    }

}

// OUTPUT:
// 02:45:34.869 [main] INFO functional.patterns.FactoryPatternMain -- fruit = Gac
// 02:45:34.873 [main] INFO functional.patterns.FactoryPatternMain -- cantaloupe = Melon(type=Cantaloupe, weight=1.3, color=Yellow)
// 02:45:34.880 [main] INFO functional.patterns.FactoryPatternMain -- watermelon = Melon(type=Watermelon, weight=2.4, color=Red)

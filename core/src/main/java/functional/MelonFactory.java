package functional;


import functional.model.*;
import functional.spec.TriFunction;

import java.util.Map;
import java.util.function.Supplier;

public class MelonFactory {

    private MelonFactory() {}


    private static final Map<String, Supplier<Fruit>> MELONS = Map.of(
            "Gac", Gac::new
            , "Hemi", Hemi::new
            , "Cantaloupe", Cantaloupe::new
    );



    public static Fruit newInstance(Class<?> clazz) {
        Supplier<Fruit> supplier = MELONS.get(clazz.getSimpleName());

        if (supplier == null) {
            throw new IllegalArgumentException("Unknown fruit type: " + clazz.getSimpleName());
        }

        return supplier.get();
    }

    private static final TriFunction<String, Float, String, Melon> MELON = Melon::new;


    public static Fruit newInstance(String name, float weight, String color) {
        return MELON.apply(name, weight, color);
    }

}

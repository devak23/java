package functional.model;

import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static functional.model.Melon.Sugar.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Melon implements Fruit {
    public static final String[] MELON_TYPES = {"Gac", "Hemi", "Apollo", "Watermelon", "Horned", "Cantaloupe"};
    enum Sugar {
        LOW(1), MEDIUM(2), HIGH(3), UNKNOWN(-1), NONE(0);

        @Getter
        private int sweetness; // higher the number, sweeter the melon
        Sugar(int i) {
            sweetness = i;
        }

    }

    private String type;
    private float weight;
    private String color;
    private Sugar sugar;

    public Melon(String type, float weight) {
        this.type = type;
        this.weight = weight;
        this.sugar = UNKNOWN;
    }

    public Melon(String type, float weight, Sugar sugar) {
        this.type = type;
        this.weight = weight;
        this.sugar = sugar;
    }

    public Melon(String type, float weight, String color) {
        this.type = type;
        this.weight = weight;
        this.color = color;
        this.sugar = UNKNOWN;
    }

    @Override
    public String getName() {
        return "Melon";
    }

    public static float growing100g(Melon melon) {
        melon.setWeight(melon.getWeight() + 100.0f);
        return melon.getWeight();
    }

    @Override
    public String toString() {
        return STR."(\{type}:\{weight})";
    }

    public static List<Melon> getMelons() {
        return Arrays.asList(new Melon("Crenshaw", 1200f),
                new Melon("Gac", 3000f), new Melon("Hemi", 2600f),
                new Melon("Hemi", 1600f), new Melon("Gac", 1200f),
                new Melon("Apollo", 2600f), new Melon("Horned", 1700f),
                new Melon("Gac", 3000f), new Melon("Hemi", 2600f),
                new Melon("Horned", 1950f)
        );
    }

    public static List<Melon> getCaseSensitiveMelonsType() {
        return Arrays.asList(new Melon("Crenshaw", 1200f),
                new Melon("Gac", 3000f), new Melon("Hemi", 2600f),
                new Melon("hemi", 1600f), new Melon("Gac", 1200f),
                new Melon("Apollo", 2600f), new Melon("Horned", 1700f),
                new Melon("gac", 3000f), new Melon("Hemi", 2600f),
                new Melon("horned", 1950f)
        );
    }

    public static List<Melon> get100Melons() {
        Random random = new Random(17);
        return IntStream.rangeClosed(1, 100).mapToObj(i -> {
            int randomInt = random.nextInt(MELON_TYPES.length);
            return new Melon(MELON_TYPES[randomInt], random.nextInt(1000, 6000));
        }).toList();
    }

    public static List<Melon> getMelonsWithSugar() {
        return Arrays.asList(new Melon("Crenshaw", 1200, MEDIUM),
                new Melon("Gac", 3000, LOW), new Melon("Hemi", 2600, HIGH),
                new Melon("Hemi", 1600), new Melon("Gac", 1200, LOW),
                new Melon("Cantaloupe", 2600, NONE), new Melon("Cantaloupe", 3600, MEDIUM),
                new Melon("Apollo", 2600, MEDIUM), new Melon("Horned", 1200, HIGH),
                new Melon("Gac", 3000, LOW), new Melon("Hemi", 2600, HIGH)
        );
    }
}

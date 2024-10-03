package functional.model;

import lombok.*;

import java.util.Arrays;
import java.util.List;

import static functional.model.Melon.Sugar.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Melon implements Fruit {

    enum Sugar {
        LOW, MEDIUM, HIGH, UNKNOWN
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
                new Melon("Gac", 3000f), new Melon("Hemi", 2600f)
        );
    }

    public static List<Melon> getMelonsWithSugar() {
        return Arrays.asList(new Melon("Crenshaw", 1200, MEDIUM),
                new Melon("Gac", 3000, LOW), new Melon("Hemi", 2600, HIGH),
                new Melon("Hemi", 1600), new Melon("Gac", 1200, LOW),
                new Melon("Cantaloupe", 2600, MEDIUM), new Melon("Cantaloupe", 3600, MEDIUM),
                new Melon("Apollo", 2600, MEDIUM), new Melon("Horned", 1200, HIGH),
                new Melon("Gac", 3000, LOW), new Melon("Hemi", 2600, HIGH)
        );
    }
}

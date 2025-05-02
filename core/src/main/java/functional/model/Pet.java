package functional.model;

import java.util.List;

public record Pet(
    String name,
    String animal,
    String breed,
    String color,
    Double price,
    String description) {

    public static final List<Pet> pets = new java.util.ArrayList<>(20);

    @Override
    public String toString() {
        return STR."name: \{name} a color: \{color} breed \{breed} of animal \{animal} for sale $\{price}";
    }

    public boolean equals(Object other) {
        if (other == null) return false;
         Pet o = (Pet) other;
        boolean incomingParamsNotNull = o.name != null && o.breed != null && o.animal != null;
        boolean thisParamsNotNull = name != null && breed != null && animal != null;

        if (incomingParamsNotNull && thisParamsNotNull) {
             return name.equals(o.name) && animal.equals(o.animal) && breed.equals(o.breed);
         } else {
             return false;
         }
    }
}

package javacodingproblems;

import java.util.HashSet;
import java.util.Set;

// Java records were delivered as a feature preview starting with JDK 14, and it was released and closed in JDK 16 as JEP 395.
public record MelonRecord(String type, float weight) {
    // Besides instance methods, we can add static fields and methods as well.
    private static final String DEFAULT_MELON_TYPE = "Crenshaw";
    private static final float DEFAULT_MELON_WEIGHT = 2_000;

    public static MelonRecord getDefaultMelon() {
        return new MelonRecord(DEFAULT_MELON_TYPE, DEFAULT_MELON_WEIGHT);
    }

    // We can have additional constructors
    MelonRecord(String type) {
        this(type, DEFAULT_MELON_WEIGHT);
    }

    MelonRecord(float weight) {
        this(DEFAULT_MELON_TYPE, weight);
    }

    private static Set<String> countries = new HashSet<>(5);
    // and a constructor which has no direct field within the record
    MelonRecord(String type, float weight, String country) {
        this(type, weight);
        MelonRecord.countries.add(country);
    }

    // explicit canonical constructor for validations
    public MelonRecord(String type, float weight) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("type cannot be blank");
        }

        if (weight < 1000 || weight > 10000) {
            throw new IllegalArgumentException("weight must be between 1000 and 10000");
        }
        this.type = type;
        this.weight = weight;
    }

    public float weightToKg() {
        return weight / 1_000;
    }
}

/*
 * javap for this class gives us:
 *
 * abhaykulkarni classes (master) >> javap javacodingproblems/Melon.class
 * Compiled from "Melon.java"
 * public final class javacodingproblems.Melon extends java.lang.Record {
 *   public javacodingproblems.Melon(java.lang.String, float);
 *   public final java.lang.String toString();
 *   public final int hashCode();
 *   public final boolean equals(java.lang.Object);
 *   public java.lang.String type();
 *   public float weight();
 * }
 */


/*
 * Limitations:
 * 1. A record cannot extend another class
 * 2. A record cannot be enriched with instance fields
 * 3. A record cannot have private canonical constructors
 * 4. A record cannot have setters
 */
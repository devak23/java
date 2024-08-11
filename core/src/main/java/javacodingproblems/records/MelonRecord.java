package javacodingproblems.records;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// Java records were delivered as a feature preview starting with JDK 14, and it was released and closed in JDK 16 as JEP 395.
public record MelonRecord(String type, float weight, String batch, LocalDate expiration) implements PestInspector, Serializable {
    // Besides instance methods, we can add static fields and methods as well.
    public static final String DEFAULT_MELON_TYPE = "Crenshaw";
    public static final float DEFAULT_MELON_WEIGHT = 2_000;
    public static final LocalDate DEFAULT_EXPIRATION = LocalDate.now().plusDays(15);
    public static final String DEFAULT_BATCH = "ML8000AC20";

    public static MelonRecord getDefaultMelon() {
        return new MelonRecord(DEFAULT_MELON_TYPE, DEFAULT_MELON_WEIGHT, DEFAULT_BATCH, DEFAULT_EXPIRATION);
    }

    // We can have additional constructors initializing various parameters...
    MelonRecord(String type) {
        this(type, DEFAULT_MELON_WEIGHT, DEFAULT_BATCH, DEFAULT_EXPIRATION);
    }

    MelonRecord(float weight) {
        this(DEFAULT_MELON_TYPE, weight, DEFAULT_BATCH, DEFAULT_EXPIRATION);
    }

    MelonRecord(String type, float weight) {
        this(type, weight, DEFAULT_BATCH, DEFAULT_EXPIRATION);
    }

    // ... and a constructor which has no direct field within the record
    private static Set<String> countries = new HashSet<>(5);
    MelonRecord(String type, float weight, String country) {
        this(type, weight, DEFAULT_BATCH, DEFAULT_EXPIRATION);
        MelonRecord.countries.add(country);
    }

    // explicit canonical constructor for validations can be defined as shown below, but this is very verbose.
    //    public MelonRecord(String type, float weight, String batch, LocalDate expiration) {
    //
    //    }

    // We would rather write the "compact constructor" as shown below. Much more conscise. If you write both the canonical
    // and the compact constructor though, you will get a compilation error. Try it!
    public MelonRecord {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be blank");
        }

        if (weight < 1000 || weight > 10000) {
            throw new IllegalArgumentException("Weight must be between 1000 and 10000");
        }

        if (!batch.startsWith("ML")) {
            throw new IllegalArgumentException("Batch format should be: MLxxxxxxxx");
        }
        // as you can see in the compact version of the constructor we only need to provide validations and there is no
        // need to explicitly initialize the constructor as it is taken care of by the specification.

    }

    public float weightToKg() {
        return weight / 1_000;
    }

    /**
     * Like regular classes, records also implement interfaces
     */
    @Override
    public void exterminatePest() {
        if (isInfected()) {
            System.out.println("Infected Melon is now cleaned. All pests have been removed");
        } else {
            System.out.println("No pests have been found!");
        }
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
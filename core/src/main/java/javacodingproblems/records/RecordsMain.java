package javacodingproblems.records;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RecordsMain {

    public static final String MELON_DATA = "./melon.data";
    public static final String MELON2_DATA = "./melon2.data";

    public static void main(String[] args) {
        var cantaloupe = new MelonRecord("Cantaloupe", 2600, "ML9000SQA0", MelonRecord.DEFAULT_EXPIRATION);
        System.out.println(cantaloupe);
        System.out.println(cantaloupe.weightToKg());
        System.out.println(MelonRecord.getDefaultMelon());

        // var watermelon = new Melon("Watermelon"); // You cannot provide partial list of fields to the constructor
        //var watermelon = new Melon("Watermelon", 26000); // this line triggers the validation and fails

        try {
            writeMelon(cantaloupe);
        } catch (Exception e) {
            System.out.println("Exception occurred when trying to write melon to file: " + e.getMessage());
        }

        MelonRecord deserialized = null;
        try {
            deserialized = readMelon(MELON2_DATA);
        } catch (Exception e) {
            System.out.println("Exception occurred when trying to write melon to file: " + e.getMessage());
        }

        boolean equals = Objects.equals(deserialized, cantaloupe);
        System.out.println("Is deserialized melon the same as serialized? " + equals);

        Map<String, Integer> marketRepo = new HashMap<>(10);
        marketRepo.put("TCS", 3113);
        marketRepo.put("HDFC", 1511);

        var marketRecord = new MarketRecord(marketRepo);
        System.out.println(marketRecord);
        marketRepo.put("TCS", 4115); // Even though we are modifying the value of TCS, it won't get picked up.
        System.out.println(marketRecord);

        // marketRecord.retails().put("TCS", 5444); // this produces UnsupportedOperationException as the map is immutable

        var existingData = marketRecord.retails();
        System.out.println(existingData);
        //existingData.put("TCS", 4554); // this too produces UnsupportedOperationException as the map is immutable
    }

    private static void writeMelon(MelonRecord melon) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MELON_DATA))) {
            oos.writeObject(melon);
        }
    }

    private static MelonRecord readMelon(String file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (MelonRecord) ois.readObject();
        }
    }
}

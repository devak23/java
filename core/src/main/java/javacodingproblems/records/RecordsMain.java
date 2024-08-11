package javacodingproblems.records;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static javacodingproblems.records.MelonRecord.*;

public class RecordsMain {

    public static final String MELON_DATA = "./melon.data";
    public static final String MELON2_DATA = "./melon2.data";

    public static void main(String[] args) {
        var cantaloupe = new MelonRecord("Cantaloupe", 2600, "ML9000SQA0", DEFAULT_EXPIRATION);
        System.out.println(cantaloupe);
        System.out.println(cantaloupe.weightToKg());
        System.out.println(getDefaultMelon());

        var watermelon = new MelonRecord("Watermelon");
        System.out.println(watermelon);
        var muskMelon = new MelonRecord("MuskMelon", 6000);
        System.out.println(muskMelon);

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


        try {
            Constructor<MelonRecord> cmr = getCanonicalConstructor(MelonRecord.class);
            MelonRecord mr1 = cmr.newInstance("GAC", 5000f, "ML9000SQA1", DEFAULT_EXPIRATION);
            MelonRecord mr2 = cmr.newInstance("Hemi", 1400f, "ML9000SQA2", DEFAULT_EXPIRATION);
            System.out.println(mr1);
            System.out.println(mr2);

            Constructor<MelonMarketRecord> cmmr = getCanonicalConstructor(MelonMarketRecord.class);
            MelonMarketRecord mmr = cmmr.newInstance(List.of(mr1, mr2), "China");
            System.out.println(mmr);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    static <T extends Record> Constructor<T> getCanonicalConstructor(Class<T> recordClass)
            throws NoSuchMethodException, SecurityException {
        Class<?>[] componentTypes = Arrays.stream(recordClass.getRecordComponents())
                .map(rc -> rc.getType())
                .toArray(Class<?>[]::new);
        return recordClass.getDeclaredConstructor(componentTypes);
    }
}

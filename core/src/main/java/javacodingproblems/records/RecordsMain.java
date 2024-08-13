package javacodingproblems.records;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.*;
import static javacodingproblems.records.MelonRecord.DEFAULT_EXPIRATION;
import static javacodingproblems.records.MelonRecord.getDefaultMelon;

@Slf4j
public class RecordsMain {

    public static final String MELON_DATA = "./melon.data";
    public static final String MELON2_DATA = "./melon2.data";

    public static void main(String[] args) {
        var cantaloupe = new MelonRecord("Cantaloupe", 2600, "ML9000SQA0", DEFAULT_EXPIRATION);
        log.info("Melon: {}", cantaloupe);
        log.info("Default melon: {}", getDefaultMelon());

        var watermelon = new MelonRecord("Watermelon");
        log.info("watermelon: {}", watermelon);
        var muskMelon = new MelonRecord("MuskMelon", 6000);
        log.info("muskMelon: {}", muskMelon);

        try {
            writeMelon(cantaloupe);
        } catch (Exception e) {
            log.error("Exception occurred when trying to write melon to file.", e);
        }

        MelonRecord deserialized = null;
        try {
            deserialized = readMelon(MELON2_DATA);
        } catch (Exception e) {
            log.error("Exception occurred when trying to write melon to file", e);
        }

        boolean equals = Objects.equals(deserialized, cantaloupe);
        log.info("Is deserialized melon the same as serialized? " + equals);


        MelonRecord mr1;
        MelonRecord mr2;
        try {
            Constructor<MelonRecord> cmr = getCanonicalConstructor(MelonRecord.class);
            mr1 = cmr.newInstance("GAC", 5000f, "ML9000SQA1", DEFAULT_EXPIRATION);
            mr2 = cmr.newInstance("Hemi", 1400f, "ML9000SQA2", DEFAULT_EXPIRATION);
            log.info("mr1: {}", mr1);
            log.info("mr2: {}", mr2);

            Constructor<MelonMarketRecord> cmmr = getCanonicalConstructor(MelonMarketRecord.class);
            MelonMarketRecord mmr = cmmr.newInstance(List.of(mr1, mr2), "China");
            log.info("mmr: {}", mmr);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        // PROBLEM: For a given list of Melons, extract the total weight and the list of weights.
        // Answer:  This data can be carried by a regular Java class or by another record 'WeightsAndTotalRecord'
        WeightsAndTotalRecord wr = List.of(cantaloupe, watermelon, muskMelon, mr1, mr2).stream()
                .collect(teeing(
                        summingDouble(MelonRecord::weight)
                        , mapping(MelonRecord::weight, toList())
                        , WeightsAndTotalRecord::new
                        )
                );
        log.info("Total weight = " + wr.totalWeight() + ", list Of weights = " + wr.weights());

        // A simple example of collecting count of elevations by elevation.
        Map<Double, Long> elevations = DoubleStream.of(
                22, -10, 100, -5, 100, 123, 22, 230, -1, 250, 22)
                .filter(e -> e > 0)
                .map(e -> e * 0.393701)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        log.info("Elevations: " + elevations);

        // The resulting data is carried by Map<Double, Long>, which is not very expressive. If we pull this map out of
        // the context (for instance, we pass it as an argument into a method), it is hard to say what Double and Long
        // represent. It would be more expressive to have something such as Map<Elevation, ElevationCount>, which
        // clearly describes its content.

        Map<Elevation, ElevationCount> elevationCountMap = DoubleStream.of(
                22, -10, 100, -5, 100, 123, 22, 230, -1, 250, 22)
                .filter(e -> e > 0)
                .mapToObj(Elevation::new)
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.collectingAndThen(counting(), ElevationCount::new)
                ));
        log.info("Elevation (with record): " + elevationCountMap);
        // Now, passing Map<Elevation, ElevationCount> to a method dispels any doubt about its content. Any team member
        // can inspect these records in the blink of an eye without losing time reading our functional implementation in
        // order to deduce what Double and Long represent. We can be even more expressive and rename the Elevation
        // record as PositiveElevation.


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Map<String, Integer> marketRepo = new HashMap<>(10);
        marketRepo.put("TCS", 3113);
        marketRepo.put("HDFC", 1511);

        var marketRecord = new MarketRecord(marketRepo);
        log.info(marketRecord.toString());
        marketRepo.put("TCS", 4115); // Even though we are modifying the value of TCS, it won't get picked up.
        log.info(marketRecord.toString());

        // marketRecord.retails().put("TCS", 5444); // this produces UnsupportedOperationException as the map is immutable

        var existingData = marketRecord.retails();
        log.info(existingData.toString());
        //existingData.put("TCS", 4554); // this too produces UnsupportedOperationException as the map is immutable
    }

    public static String cabinet(Staff staff) {
        if (staff instanceof Doctor(String name, String speciality)) { // type pattern matching
            return "Cabinet of: " + speciality + ". Doctor: " + name;
        }

        throw new IllegalArgumentException("Invalid staff");
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

    record Elevation(double value) {
        Elevation(double value) {
            this.value = value * 0.393701;
        }
    }

    record ElevationCount(long count) {}

    interface Staff {}

    record Doctor(String name, String speciality) implements Staff {}
}

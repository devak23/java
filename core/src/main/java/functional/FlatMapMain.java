package functional;

import functional.model.KafkaWrapper;
import functional.model.StageRecord;

import java.util.*;

public class FlatMapMain {

    public static void main(String[] args) {
        KafkaWrapper wrapper = getMapOfMaps();

        wrapper.getMessages().values()
                .stream()
                .flatMap(stageRecord -> stageRecord.getFields().values().stream())
                .forEach(System.out::println);

        List<Integer> numbersWithNulls = getNumbersWithNulls();
        System.out.println(numbersWithNulls);
        numbersWithNulls.stream().filter(Objects::nonNull).forEach(System.out::println);
    }

    private static KafkaWrapper getMapOfMaps() {
        Map<String, String> fields1 = new HashMap<>();
        fields1.put("F1", "arts");
        fields1.put("F2", "science");
        fields1.put("F3", "commerce");
        StageRecord rec1 = new StageRecord(fields1);

        Map<String, String> fields2 = new HashMap<>();
        fields2.put("N1", "abhay");
        fields2.put("N2", "guru");
        fields2.put("N3", "tejas");
        StageRecord rec2 = new StageRecord(fields2);

        KafkaWrapper wrapper = new KafkaWrapper();
        wrapper.getMessages().put("fields", rec1);
        wrapper.getMessages().put("names", rec2);
        return wrapper;
    }

    private static List<Integer> getNumbersWithNulls() {
        var listOfNumbers = new ArrayList<Integer>(10);
        listOfNumbers.add(1);
        listOfNumbers.add(null);
        listOfNumbers.add(3);
        listOfNumbers.add(5);
        listOfNumbers.add(null);
        listOfNumbers.add(9);
        listOfNumbers.add(null);
        listOfNumbers.add(11);
        return listOfNumbers;
    }
}

package javacodingproblems;

import java.util.HashMap;
import java.util.Map;

public class RecordsMain {

    public static void main(String[] args) {
        var cantaloupe = new MelonRecord("Cantaloupe", 2600);
        System.out.println(cantaloupe);
        System.out.println(cantaloupe.weightToKg());
        System.out.println(MelonRecord.getDefaultMelon());

        // var watermelon = new Melon("Watermelon"); // You cannot provide partial list of fields to the constructor
        //var watermelon = new Melon("Watermelon", 26000); // this line triggers the validation and fails

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
        existingData.put("TCS", 4554); // this too produces UnsupportedOperationException as the map is immutable
    }
}

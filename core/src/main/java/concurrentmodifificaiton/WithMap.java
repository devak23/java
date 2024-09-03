package concurrentmodifificaiton;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WithMap {

    public static void main(String[] args) {
        Person person = new Person();
        person.getStocks().put("TCS", "Tata Consultancy Services");
        person.getStocks().put("WIPRO", "Wipro India Ltd");
        person.getStocks().put("STYM", "Satyam");
        person.getStocks().put("INFY", "Infosys");
        person.getStocks().put("REL", "Reliance");


        HashMap<String, String> moreStocks = new HashMap<>();
        moreStocks.put("CGPOWER", "CG Power Ltd");
        moreStocks.put("AXIS", "Axis Bank");
        moreStocks.put("ADANI", "Adani Enterprises");

        safeModification(person, moreStocks);
    }

    public static void safeModification(Person person, Map<String, String> moreStocks ) {
        Map<String, String> stocks = person.getStocks();
        Iterator<Map.Entry<String, String>> iterator = stocks.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            if (key.equals("STYM")) {
                iterator.remove();
            }

            if (moreStocks.containsKey("ADANI")) {
                stocks.put("ADANI", moreStocks.get("ADANI"));
            }

            if (key.equals("REL")) {
                iterator.remove();
            }

            if (moreStocks.containsKey("CGPOWER")) {
                stocks.put("CGPOWER", moreStocks.get("CGPOWER"));
            }
        }

        System.out.println("Final List of stocks: " + stocks);
    }
}

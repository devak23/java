package javacodingproblems;

import java.util.Map;

// To create defensive copies, one needs to ensure that objects cannot be modified.
public record MarketRecord(Map<String, Integer> retails) {

    public MarketRecord {
        retails = Map.copyOf(retails);
    }
    // This is basically a flavor of component reassignment

    public Map<String, Integer> retails() {
        return Map.copyOf(retails);
    }
}

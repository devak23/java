package functional.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class StageRecord {
    private Map<String, String> fields = new HashMap<>(10);

    public StageRecord(Map<String, String> fields1) {
        fields.putAll(fields1);
    }
}

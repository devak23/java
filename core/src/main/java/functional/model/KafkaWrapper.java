package functional.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class KafkaWrapper {
    private Map<String, StageRecord> messages = new HashMap<>(10);
}

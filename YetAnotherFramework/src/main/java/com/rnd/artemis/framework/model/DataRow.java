package com.rnd.artemis.framework.model;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ToString
public class DataRow {
    private String correlationId; // kafka offsetId, partitionName
    private Map<String, ? super Object> row = new HashMap<>(30);

    public Optional<? super Object> get(String key) {
        return Optional.ofNullable(row.get(key));
    }

    public DataRow set(String key, Object value) {
        row.put(key, value);
        return this;
    }
}

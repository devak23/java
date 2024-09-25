package com.rnd.artemis.framework.model;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Data {
    private Map<String, DataRow> data = new LinkedHashMap<>(30);
    private Mode mode;

    public boolean isRealTime() {
        return mode == Mode.REALTIME;
    }
}

package com.ak.rnd.architecture.model;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class Datasource {
    private String name;
    private String type;
    private Map<String, String> endpoints;
}
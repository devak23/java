package com.ak.rnd.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties
public class BaseConfig {
    protected String name;
    protected List<? extends BaseModule> modules;
}

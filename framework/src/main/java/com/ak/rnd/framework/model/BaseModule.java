package com.ak.rnd.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties
public class BaseModule {
    protected String name;
    protected String source;
    protected String type;
}

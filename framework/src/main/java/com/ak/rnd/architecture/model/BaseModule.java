package com.ak.rnd.architecture.model;

import com.ak.rnd.architecture.processor.IProcess;
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
    protected IProcess<String> processor;
}

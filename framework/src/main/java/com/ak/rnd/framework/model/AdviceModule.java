package com.ak.rnd.framework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class AdviceModule extends BaseModule {
    private int priority;
    private List<String> mode;
    private String dependency;
}

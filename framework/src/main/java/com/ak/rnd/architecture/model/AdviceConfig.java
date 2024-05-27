package com.ak.rnd.architecture.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class AdviceConfig extends BaseConfig {
    private List<Datasource> datasources;
    private List<AdviceModule> modules;


    @Override
    public void setModules(List<? extends BaseModule> modules) {
        this.modules = (List<AdviceModule>) modules;
    }

    @Override
    public List<? extends BaseModule> getModules() {
        return modules;
    }
}

package com.rnd.sage.framework.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProcessorConfig extends BaseConfig {
    private List<ModuleConfig> modules;
}

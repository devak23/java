package com.rnd.artemis.framework.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ActionConfig extends BaseConfig {
    private List<ConditionalConfig> conditionalConfig;
    private String configurationFile;
}

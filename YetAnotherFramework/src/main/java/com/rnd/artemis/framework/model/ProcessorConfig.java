package com.rnd.artemis.framework.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Component
public class ProcessorConfig extends BaseConfig {
    private List<ModuleConfig> modules;
}

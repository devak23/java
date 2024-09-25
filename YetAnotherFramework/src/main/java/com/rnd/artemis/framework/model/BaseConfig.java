package com.rnd.artemis.framework.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BaseConfig {
    protected String name;
    protected String code;
    protected String description;
    protected String qualifier;
    protected boolean enabled = true;
}

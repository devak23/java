package com.rnd.app.framework.ch01.domain;

import lombok.ToString;

@ToString
public class RouterId {
    private String id;

    public RouterId(String id) {
        this.id = id;
    }

    public static RouterId of(String id) {
        return new RouterId(id);
    }
}

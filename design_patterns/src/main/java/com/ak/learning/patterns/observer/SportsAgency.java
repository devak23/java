package com.ak.learning.patterns.observer;

import java.util.Observable;

public class SportsAgency extends Observable {
    private String name;

    public String getName() {
        return name;
    }

    public SportsAgency(String name) {
        this.name = name;
    }


}

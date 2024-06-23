package com.rnd.app.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Segment(Point first,
                      Point second) {

    public BigDecimal distance() {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);

        return first.distance(second);
    }
}

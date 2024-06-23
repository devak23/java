package com.rnd.app.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public record Segment(Point first,
                      Point second) {

    public BigDecimal distance() {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);

        return first.distance(second);
    }

    public boolean includesAnyPoint(List<Point> points) {
        return points.stream()
                .anyMatch(p -> p.equals(first) || p.equals(second));
    }
}

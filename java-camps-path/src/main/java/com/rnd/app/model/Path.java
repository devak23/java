package com.rnd.app.model;

import java.math.BigDecimal;
import java.util.List;

public record Path(List<Segment> segments) {

    public BigDecimal distance() {
        return segments.parallelStream()
                .map(Segment::distance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public boolean includesPoint(Point includingPoint) {
        return segments.stream()
                .anyMatch(s -> s.includesPoint(includingPoint));
    }
}

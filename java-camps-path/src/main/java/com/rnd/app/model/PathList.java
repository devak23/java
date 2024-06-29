package com.rnd.app.model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public record PathList(List<Path> paths) {

    public Optional<Path> shortestPath() {
        return shortestPathWithCondition(p -> true);
    }

    public Optional<Path> shortestPath(List<Point> points) {
        return shortestPathWithCondition(p -> p.includesPoints(points));
    }

    public Optional<Path> shortestPathWithCondition(Predicate<Path> condition) {
        return paths.parallelStream()
                .filter(condition)
                .min(Comparator.comparing(Path::distance));
    }
}

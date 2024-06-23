package com.rnd.app.model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record PathList(List<Path> paths) {
    public Optional<Path> shortestPath() {
        return paths.parallelStream().min(Comparator.comparing(Path::distance));
    }

    public Optional<Path> shortestPath(List<Point> includingPoint) {
        return paths.parallelStream()
                .filter(p -> p.includesPoints(includingPoint))
                .min(Comparator.comparing(Path::distance));
    }
}

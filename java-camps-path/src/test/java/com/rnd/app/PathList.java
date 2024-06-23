package com.rnd.app;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record PathList(List<Path> paths) {
    public Optional<Path> shortestPath() {
        return paths.parallelStream().min(Comparator.comparing(Path::distance));
    }
}

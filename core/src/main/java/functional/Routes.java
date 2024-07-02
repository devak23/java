package functional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record Routes(List<Path> paths) {

    // A route could be made up of one more many paths. So the shortest route is the one where the path distance is
    // the shortest
    public Optional<Path> shortestRoute() {
        return paths.stream().min((p1, p2) -> p1.distance().compareTo(p2.distance()));
    }

    // We want to find the shortest route including a given list of points.
    public Optional<Path> shortestRouteIncluding(List<Point> points) {
        return paths.stream()
                .filter(p -> {
                    return p.lines().stream().filter(l -> {
                        return points.contains(l.p1()) && points.contains(l.p2());
                    }).findAny().isPresent();
                })
                .min(Comparator.comparing(Path::distance));
    }
}

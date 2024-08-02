package functional.model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * A route could be made up of one more many paths. So
 */
public record Routes(List<Path> paths) {

    // the shortest route is the one where the distance of the path is the shortest
    public Optional<Path> shortestRoute() {
        return paths.stream().min((p1, p2) -> p1.length().compareTo(p2.length()));
    }

    // What if we wanted to find the shortest route given list of points.
    public Optional<Path> shortestRouteIncluding(List<Point> points) {
        return paths.stream()
                .filter(path -> path.including(points))
                .min(Comparator.comparing(Path::length));
    }

//    public Optional<Path> shortestRouteIncluding(List<Point> points) {
//        return paths.stream()
//                .filter(p -> {
//                    return p.lines().stream().filter(l -> {
//                        return points.contains(l.p1()) && points.contains(l.p2());
//                    }).findAny().isPresent();
//                })
//                .min(Comparator.comparing(Path::length));
//    }
}

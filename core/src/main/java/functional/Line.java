package functional;

import java.math.BigDecimal;
import java.util.List;

public record Line(Point p1, Point p2) {

    // distance is generally the straight line between the two points
    public BigDecimal distance() {
        return p1.distance(p2);
    }

    public boolean includes(List<Point> points) {
        return points.stream().anyMatch(p -> p.equals(p1) || p.equals(p2));
    }
}

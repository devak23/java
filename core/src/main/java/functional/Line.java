package functional;

import java.math.BigDecimal;
import java.util.List;

/**
 * A line can be drawn connecting two points say P1 and P2
 */
public record Line(Point p1, Point p2) {
    // so what is the distance between two points?
    // ... generally a straight line right?
    public BigDecimal distance() {
        return p1.distance(p2);
    }

    public boolean includesAny(List<Point> points) {
        return points.contains(p1) || points.contains(p2);
    }
}

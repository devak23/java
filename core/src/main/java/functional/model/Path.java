package functional.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * A Path is simply a collection of lines
 */
public record Path(List<Line> lines) {
    // so the distance or length of the path would be summation of all the lines
    public BigDecimal length() {
        return lines.stream()
                .map(Line::distance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public boolean including(List<Point> points) {
        return lines.stream().anyMatch(line -> line.includesAny(points));
    }
}

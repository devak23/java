package functional;

import java.math.BigDecimal;
import java.util.List;

public record Path(List<Line> lines) {

    public BigDecimal distance() {
        return lines.stream()
                .map(Line::distance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public boolean including(List<Point> points) {
        return lines.stream().anyMatch(l -> l.includes(points));
    }
}

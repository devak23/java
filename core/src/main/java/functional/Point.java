package functional;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Imagine that you have a class called Point which is defined by the co-ordinate system having parameters x and y
 */
public record Point(BigDecimal x, BigDecimal y) {
    // How will you find the distance between a given point with other point?

    public BigDecimal distance(Point other) {
        // Hypotenuse is the distance between the two points given by d = sqrt(x^2 + y2)
        return x.subtract(other.x).pow(2)
                .add(y.subtract(other.y).pow(2))
                .sqrt(MathContext.DECIMAL64);
    }
}

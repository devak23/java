package lambdas.area;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Square extends Shape {
    private SquareDimensions dimensions;

    public Square(SquareDimensions dimensions) {
        this.dimensions = dimensions;
    }

    public Area getArea() {
        return () -> dimensions.length * dimensions.length;
    }

    @Getter
    @Builder
    static final class SquareDimensions extends Dimensions {
        private double length;
    }
}

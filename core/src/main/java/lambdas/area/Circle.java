package lambdas.area;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Circle extends Shape {
    private final CircleDimensions dimensions;

    public Circle(CircleDimensions dimensions) {
        this.dimensions = dimensions;
    }

    public Area getArea() {
        return () -> dimensions.radius * dimensions.radius * CircleDimensions.PI;
    }

    @Getter
    @Builder
    static final class CircleDimensions extends Dimensions {
        public static final double PI = 3.1415d;
        private final double radius;
    }
}

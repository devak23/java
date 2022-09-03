package lambdas.area;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Rectangle extends Shape {
    private RectangleDimensions dimensions;

    public Rectangle(RectangleDimensions dimensions) {
        this.dimensions = dimensions;
    }

    public Area getArea() {
        return () -> dimensions.length * dimensions.width;
    }


    @Getter
    @Builder
    static final class RectangleDimensions {
        private double length;
        private double width;
    }
}

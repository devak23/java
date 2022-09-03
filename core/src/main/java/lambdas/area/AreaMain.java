package lambdas.area;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AreaMain {

    public static void main(String[] args) {
        Circle circle = new Circle(new Circle.CircleDimensions(3.1415d));
        Rectangle rectangle = new Rectangle(new Rectangle.RectangleDimensions(3.2d, 4.5d));
        Square square = new Square(new Square.SquareDimensions(5.657d));

        System.out.println("Area of a circle = " + BigDecimal.valueOf(circle.getArea().calculate()).setScale(2,RoundingMode.CEILING).doubleValue());
        System.out.println("Area of a rectangle = " + BigDecimal.valueOf(rectangle.getArea().calculate()).setScale(2, RoundingMode.CEILING).doubleValue());
        System.out.println("Area of a square = " + BigDecimal.valueOf(square.getArea().calculate()).setScale(2, RoundingMode.CEILING).doubleValue());
    }
}

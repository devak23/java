package functional;


import functional.model.Line;
import functional.model.Point;
import org.junit.Test;

import java.util.List;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.*;

public class LineTest {

    @Test
    public void givenLine_whenPassedAListOfPoints_thenContainsReturnsTrue() {
        // given
        Point oneOne = new Point(ONE, ONE);
        Point oneZero = new Point(ONE, ZERO);
        Line line = new Line(oneZero, oneOne);
        List<Point> points = List.of( new Point(ZERO, ZERO)
                , new Point(ONE, ZERO)
                , new Point(ZERO, ONE)
                , new Point(ONE, ONE)
                , new Point(TEN, ONE)
        );

        // when
        boolean actual = line.includesAny(points);

        // then
        assertThat(actual).isEqualTo(true);


    }
}
package com.rnd.app.fixtures;

import com.rnd.app.model.Point;

import java.math.BigDecimal;

public final class PointFixture {

    public static Point getPoint00() {
        return new Point(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public static Point getPoint01() {
        return new Point(BigDecimal.ZERO, BigDecimal.ONE);
    }

    public static Point getPoint11() {
        return new Point(BigDecimal.ONE, BigDecimal.ONE);
    }

    public static Point getPoint10() {
        return new Point(BigDecimal.ONE, BigDecimal.ZERO);
    }

    public static Point getPoint101() {
        return new Point(BigDecimal.TEN, BigDecimal.ONE);
    }
}

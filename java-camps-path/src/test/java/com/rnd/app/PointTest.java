package com.rnd.app;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class PointTest{

    @Test
    void givenPointIsZero_whenDistanceIsInvoked_thenThrowsNullPointerException() {
        var point = new Point(BigDecimal.ZERO, BigDecimal.ZERO);

        assertThatThrownBy(() -> point.distance(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
        void givenTwoValidPoints_whenDistanceIsInvoked_thenReturnsValidDistance() {
        var point_1 = new Point(BigDecimal.ZERO, BigDecimal.ZERO);
        var point_2 = new Point(BigDecimal.ZERO, BigDecimal.ONE);

        BigDecimal actual = point_1.distance(point_2);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(BigDecimal.ONE);
    }
}
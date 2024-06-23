package com.rnd.app.model;


import com.rnd.app.fixtures.PointFixture;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class PointTest{

    @Test
    void givenPointIsZero_whenDistanceIsInvoked_thenThrowsNullPointerException() {
        var point = PointFixture.getPoint00();

        assertThatThrownBy(() -> point.distance(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
        void givenTwoValidPoints_whenDistanceIsInvoked_thenReturnsValidDistance() {
        var point_1 = PointFixture.getPoint00();
        var point_2 = PointFixture.getPoint01();

        BigDecimal actual = point_1.distance(point_2);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(BigDecimal.ONE);
    }
}
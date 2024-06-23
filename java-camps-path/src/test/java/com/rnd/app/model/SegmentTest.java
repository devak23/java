package com.rnd.app.model;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SegmentTest {

    @Test
    void givenValidSegment_whenDistanceIsInvoked_thenReturnDistance() {
        var segment = new Segment(
                new Point(BigDecimal.ZERO, BigDecimal.ZERO),
                new Point(BigDecimal.ZERO, BigDecimal.ONE)
        );

        var actual = segment.distance();
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void givenInvalidSegment_whenDistanceIsInvoked_thenThrowNullPointerException() {
        var segment = new Segment(
          null,
          new Point(BigDecimal.ZERO, BigDecimal.ZERO)
        );

        assertThatThrownBy(segment::distance).isInstanceOf(NullPointerException.class);
    }
}
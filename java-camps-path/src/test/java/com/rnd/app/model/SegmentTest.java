package com.rnd.app.model;


import com.rnd.app.fixtures.PointFixture;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SegmentTest {

    @Test
    void givenValidSegment_whenDistanceIsInvoked_thenReturnDistance() {
        var segment = new Segment(
                PointFixture.getPoint00(),
                PointFixture.getPoint01()
        );

        var actual = segment.distance();
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void givenInvalidSegment_whenDistanceIsInvoked_thenThrowNullPointerException() {
        var segment = new Segment(
          null,
          PointFixture.getPoint00()
        );

        assertThatThrownBy(segment::distance).isInstanceOf(NullPointerException.class);
    }
}
package com.rnd.app.model;


import com.rnd.app.fixtures.PointFixture;
import com.rnd.app.fixtures.SegmentFixture;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

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

    @Test
    void givenASegment_whenIncludesIsInvokedWithEmpty_thenReturnFalse() {
        var segment = SegmentFixture.getSegment0001();
        assertThat(segment.includesAnyPoint(Collections.emptyList())).isEqualTo(false);
    }

    @Test
    void givenASegment_whenIncludesIsInvokedWithNotIncludedPoints_thenReturnsFalse() {
        var segment = SegmentFixture.getSegment0001();
        assertThat(segment.includesAnyPoint(
                List.of(PointFixture.getPoint101(), PointFixture.getPoint11()))
        ).isEqualTo(false);
    }
    @Test
    void givenASegment_whenIncludesIsInvokedWithIncludedPoints_thenReturnsTrue() {
        var segment = SegmentFixture.getSegment0011();
        assertThat(segment.includesAnyPoint(
                List.of(PointFixture.getPoint101(), PointFixture.getPoint11()))
        ).isEqualTo(true);
    }
}
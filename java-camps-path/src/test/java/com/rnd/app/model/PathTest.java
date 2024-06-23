package com.rnd.app.model;

import com.rnd.app.fixtures.SegmentFixture;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @Test
    void testDistanceForValidSegments() {
        var path = new Path(Arrays.asList(
                SegmentFixture.getSegment0001(), SegmentFixture.getSegment0111()
        ));

        BigDecimal actual = path.distance();
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(BigDecimal.TWO);
    }
}
package com.rnd.app.model;

import com.rnd.app.fixtures.PathFixture;
import com.rnd.app.fixtures.PointFixture;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PathListTest {

    @Test
    void testShortestPath() {
        Path path1 = PathFixture.getPath0001_0011();
        Path path2 = PathFixture.getPath0000_10101();
        PathList pathList = new PathList(List.of(path1, path2));
        var actualOp = pathList.shortestPath();
        assertThat(actualOp).isPresent();
        assertThat(actualOp.get()).isEqualTo(path1);
    }

    @Test
    void testShortestPathIncludingPoint() {
        Path path1 = PathFixture.getPath0001_0011();
        Point includingPoint = PointFixture.getPoint101();
        Path path2 = new Path(
                List.of(
                        new Segment(
                                PointFixture.getPoint00(),
                                PointFixture.getPoint01()
                        ),
                        new Segment(
                                includingPoint,
                                PointFixture.getPoint11()
                        )
                )
        );

        PathList pathList = new PathList(List.of(path1, path2));
        var actualOp = pathList.shortestPath(List.of(includingPoint));
        assertThat(actualOp).isPresent();
        assertThat(actualOp.get()).isEqualTo(path2);
    }

    @Test
    void testShortestPathWhenEmpty() {
        PathList pathList = new PathList(Collections.emptyList());
        var actualOp = pathList.shortestPath();
        assertThat(actualOp).isNotPresent();
    }

}
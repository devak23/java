package com.rnd.app;

import com.rnd.app.fixtures.PathFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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

}
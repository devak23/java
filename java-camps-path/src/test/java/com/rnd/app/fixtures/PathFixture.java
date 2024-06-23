package com.rnd.app.fixtures;

import com.rnd.app.model.Path;

import java.util.List;

public final class PathFixture {

    public static Path getPath0001_0011() {
        return new Path(
                List.of(
                        SegmentFixture.getSegment0001(),
                        SegmentFixture.getSegment0011()
                )
        );
    }

    public static Path getPath0000_10101() {
        return new Path(
                List.of(SegmentFixture.getSegment0000(),
                        SegmentFixture.getSegment10101())
        );
    }
}

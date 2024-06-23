package com.rnd.app.fixtures;

import com.rnd.app.Segment;

public final class SegmentFixture {

    public static Segment getSegment0001() {
        return new Segment(
                PointFixture.getPoint00(),
                PointFixture.getPoint01()
        );
    }

    public static Segment getSegment0011() {
        return new Segment(
                PointFixture.getPoint00(),
                PointFixture.getPoint11()
        );
    }

    public static Segment getSegment0000() {
        return new Segment(
                PointFixture.getPoint00(),
                PointFixture.getPoint00()
        );
    }

    public static Segment getSegment0111() {
        return new Segment(
                PointFixture.getPoint01(),
                PointFixture.getPoint11()
        );
    }

    public static Segment getSegment10101() {
        return new Segment(
                PointFixture.getPoint101(),
                PointFixture.getPoint11()
        );
    }
}

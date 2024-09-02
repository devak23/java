package dates;

import java.util.concurrent.TimeUnit;

public final class NanoStopwatch {

    private long startTime;
    private long stopTime;
    private boolean running;

    public void start() {
        startTime = System.nanoTime();
        running = true;
    }

    public void stop() {
        stopTime = System.nanoTime();
        running = false;
    }

    public long timeElapsedInNanoSeconds() {
        if (running) {
            return System.nanoTime() - startTime;
        } else {
            return stopTime - startTime;
        }
    }

    public long timeElapsedInMilliseconds() {

        if (running) {
            return TimeUnit.MILLISECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);
        } else {
            return TimeUnit.MILLISECONDS.convert(stopTime - startTime, TimeUnit.NANOSECONDS);
        }
    }
}

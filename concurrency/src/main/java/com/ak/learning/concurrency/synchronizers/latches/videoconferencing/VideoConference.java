package com.ak.learning.concurrency.synchronizers.latches.videoconferencing;

import java.util.concurrent.CountDownLatch;

/**
 * The class that implements the VideoConferencing
 */
public class VideoConference implements Runnable {
    // Conference will not start till all the participants arrive. This is controlled by
    // a count down latch.
    private final CountDownLatch controller;

    public VideoConference(int number) {
        controller = new CountDownLatch(number);
    }

    /**
     * This method decrements the latch once the participant has joined
     *
     * @param participant - the name of the participant
     */
    public void join(String participant) {
        System.out.printf("%s has arrived\n", participant);
        controller.countDown();
        System.out.printf("Waiting for %d participants to join the conference\n", controller.getCount());
    }

    /**
     * The conference object itself will be executed in a thread and in this thread
     * it will wait for all the participants to join. The await() method below does
     * exactly that. The run method DOES NOT terminate till all the participants join
     * After all joins, the conference thread comes out of the blocked state and the
     * execution of the run method is completed
     */
    @Override
    public void run() {
        System.out.printf("VideoConference: Initialization: %d participants\n", controller.getCount());
        try {
            controller.await();
            System.out.printf("VideoConference: All participants have arrived. Lets begin the meeting.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

package com.ak.learning.concurrency.synchronizers.latches.videoconferencing;

import java.util.concurrent.TimeUnit;

/**
 * A Participant class that connects to VideoConference
 */
public class Participant implements Runnable {
    // store a reference of the conference object to invoke the "Arrive" method on it
    private VideoConference conference;
    // Name of the participant
    private String name;

    public Participant(VideoConference conference, String name) {
        this.conference = conference;
        this.name = name;
    }
    @Override
    public void run() {
        // Each participant takes some time to join the conference based on a certain delay
        long duration = (long)(Math.random() * 10); // simulating the delay
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // The number is dialed and the participant joins the conference
        conference.join(name);
    }
}

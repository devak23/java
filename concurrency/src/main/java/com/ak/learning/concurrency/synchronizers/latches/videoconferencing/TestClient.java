package com.ak.learning.concurrency.synchronizers.latches.videoconferencing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The test client which tests the video conferencing action
 */
public class TestClient {
    // a dummy list of names who are going to join the conference
    private static String[] names = new String[]{
            "Sandeep", "Abhay", "Datta"
            , "Vidyadhar", "Akshayee", "Komal"
            , "Ranjit", "Pratima", "Madhuri",
            "Hemant", "Nilesh"
    };

    public static void main(String[] args) {
        // create an object of Video Conference
        VideoConference conference = new VideoConference(10);

        // Start the conference
        Thread conferenceThread = new Thread(conference);
        conferenceThread.start();

        // Create a list of participants and start them too
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            Participant p = new Participant(conference, names[i]);
            pool.submit(p);
        }

        // Shutdown the conference when done
        pool.shutdown();
    }
}

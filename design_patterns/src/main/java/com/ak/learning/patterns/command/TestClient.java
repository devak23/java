package com.ak.learning.patterns.command;

public class TestClient {
    public static void main(String... args) {
        RemoteControl rc = new RemoteControl();
        rc.switchOnTv();
        rc.goToChannel(4);
        rc.volumeUp();
        rc.volumeDown();
        rc.switchOffTv();
    }
}

package com.ak.learning.patterns.command;

public class VolumeUpCommand implements Command {
    private TV tv;

    public VolumeUpCommand(TV instance) {
        this.tv = instance;
    }

    @Override
    public void execute() {
        this.tv.increaseVolume();
    }
}

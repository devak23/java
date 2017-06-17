package com.ak.learning.patterns.command;

public class VolumeDownCommand implements Command {
    private TV tv;

    public VolumeDownCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        this.tv.decreaseVolume();
    }
}

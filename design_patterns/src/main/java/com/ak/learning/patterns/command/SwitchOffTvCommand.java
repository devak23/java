package com.ak.learning.patterns.command;

public class SwitchOffTvCommand implements Command {
    private final TV tv;

    public SwitchOffTvCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        this.tv.switchOff();
    }
}

package com.ak.learning.patterns.command;

public class SwitchOnTvCommand implements Command {
    private final TV tv;

    public SwitchOnTvCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        this.tv.switchOn();
    }
}

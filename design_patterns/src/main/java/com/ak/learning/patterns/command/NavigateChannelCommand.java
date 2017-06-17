package com.ak.learning.patterns.command;


public class NavigateChannelCommand implements Command {
    private final TV tv;
    private int channel;

    public NavigateChannelCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        this.tv.goToChannel(channel);
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}

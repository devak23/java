package com.ak.learning.patterns.command;

public class RemoteControl {
    private Command volumeUpCommand = new VolumeUpCommand(TV.INSTANCE);
    private Command volumeDownCommand = new VolumeDownCommand(TV.INSTANCE);
    private Command switchOnTvCommand = new SwitchOnTvCommand(TV.INSTANCE);
    private Command switchOffTvCommand = new SwitchOffTvCommand(TV.INSTANCE);
    private Command navigateChannelCommand = new NavigateChannelCommand(TV.INSTANCE);

    public RemoteControl() {
    }

    public void switchOnTv() {
        switchOnTvCommand.execute();
    }

    public void goToChannel(int channel) {
        ((NavigateChannelCommand)navigateChannelCommand).setChannel(channel);
        navigateChannelCommand.execute();
    }

    public void volumeUp() {
        volumeUpCommand.execute();
    }

    public void volumeDown() {
        volumeDownCommand.execute();
    }

    public void switchOffTv() {
        switchOffTvCommand.execute();
    }
}

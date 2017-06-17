package com.ak.learning.patterns.command;

import java.util.ArrayList;
import java.util.List;

public class RemoteControl {
    private TV tv = TV.getInstance();
    private VolumeUpCommand volumeUpCommand = new VolumeUpCommand(tv);
    private VolumeDownCommand volumeDownCommand = new VolumeDownCommand(tv);
    private SwitchOnTvCommand switchOnTvCommand = new SwitchOnTvCommand(tv);
    private SwitchOffTvCommand switchOffTvCommand = new SwitchOffTvCommand(tv);
    private NavigateChannelCommand navigateChannelCommand = new NavigateChannelCommand(tv);

    public RemoteControl() {
    }

    public void switchOnTv() {
        switchOnTvCommand.execute();
    }

    public void goToChannel(int channel) {
        navigateChannelCommand.setChannel(channel);
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

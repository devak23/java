Interfaces:
-----------
Command:                this is the heart of the command pattern. It contains only one method: execute(). It is
                        important to note that this interface is stateless and does NOT depend on any other
                        classes/objects

Classes:
--------
SwitchOnCommand:        implements the Command interface and provides the implementation of Switching on the TV by
                        delegating the call to the TV object
SwitchOffCommand:       implements the Command interface and provides the implementation of Switching off the TV by
                        delegating the call to the TV object
VolumeUpCommand:        implements the Command interface and provides the implementation of turning up the volume
                        on the TV by delegating the call to the TV object
VolumeDownCommand:      implements the Command interface and provides the implementation of turning down the volume
                        on the TV by delegating the call to the TV object
NavigateChannelCommand: implements the Command interface and provides the implementation of changing the tv
                        channels by delegating the call to the TV object


TV:                     The singleton class on which all the commands act. The instance of TV will be passed to
                        each of the command object. That's how the command objects are able to work their
                        functions on the TV.
RemoteControl:          The class which consists of all the commands. Ideally this class should implement a
                        RemoteControl interface (ignored for brevity in this case).
TestClient:             A client program that will use the RemoteControl object to operate the TV.


So what's the point of this pattern?
- The point is, the client has a RemoteControl which doesn't know how the TV operates. All it knows is the buttons
it has all implement the same interface with just one method execute(). So from RemoteControl's perspective, the
job is very straightforward i.e. invoke execute() on the button. The collection of buttons like VolumeUpCommand,
VolumeDownCommand etc. dont know the working of the tv either. Their only job is to delegate the call to the TV
and invoke the proper function on the TV. So what we have done is:
1. Loose coupling i.e - Programmed to the interface.
2. Clear separation of responsibilities
3.
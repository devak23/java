package functional.model;

import functional.spec.Command;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SequenceOfCommands {
    private final List<Command<Person, String>> commands = new ArrayList<>();

    public void registerCommand(Command<Person, String> command) {
        commands.add(command);
    }

    public void deregisterCommand(Command<Person, String> command) {
        commands.remove(command);
    }

    public void execute(Person person) {
        commands.forEach(command -> {log.info(command.execute(person));});
    }
}

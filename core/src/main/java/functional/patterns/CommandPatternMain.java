package functional.patterns;

import functional.model.*;
import functional.util.CommonUtil;

public class CommandPatternMain {

    public static void main(String[] args) {
        Person person = CommonUtil.fetchPersonName(5);

        SequenceOfCommands sequenceOfCommands = new SequenceOfCommands();
        sequenceOfCommands.registerCommand(new WorkCommand());
        sequenceOfCommands.registerCommand(new PlayCommand());
        sequenceOfCommands.registerCommand(new TripCommand());
        sequenceOfCommands.registerCommand(new SolveProblemCommand());

        sequenceOfCommands.execute(person);
    }
}

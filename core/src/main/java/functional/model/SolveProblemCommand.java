package functional.model;

import functional.spec.Command;

public class SolveProblemCommand implements Command<Person, String> {
    @Override
    public String execute(Person input) {
        return STR."\{input.fullName()} is asked to solve a problem";
    }
}

package functional.model;

import functional.spec.Command;

public class PlayCommand implements Command<Person, String> {
    @Override
    public String execute(Person input) {
        return STR."\{input.fullName()} is asked to play with kids";
    }
}

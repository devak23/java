package functional.model;

import functional.spec.Command;

public class TripCommand implements Command<Person, String> {
    @Override
    public String execute(Person input) {
        return STR."\{input.fullName()} is asked to go on a trip with friends";
    }
}

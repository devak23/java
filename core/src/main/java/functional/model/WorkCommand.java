package functional.model;

import functional.spec.Command;

public class WorkCommand implements Command<Person, String> {
    @Override
    public String execute(Person person) {
        return STR."\{person.fullName()} is asked to work for the family";
    }
}

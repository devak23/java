package functional.model;

import functional.spec.Cake;

public class CreamDecorator extends CakeDecorator {
    public CreamDecorator(Cake cake) {
        super(cake);
    }

    @Override
    public String decorate() {
        return super.decorate() + decorateWithCream();
    }

    private String decorateWithCream() {
        return " + cream added";
    }
}

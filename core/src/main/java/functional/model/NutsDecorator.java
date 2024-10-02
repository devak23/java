package functional.model;

import functional.spec.Cake;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NutsDecorator extends CakeDecorator {
    public NutsDecorator(Cake cake) {
        super(cake);
    }

    @Override
    public String decorate() {
        return super.decorate() + decorateWithNuts();
    }

    private String decorateWithNuts() {
        return " + nuts added";
    }
}

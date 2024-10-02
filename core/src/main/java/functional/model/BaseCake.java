package functional.model;

import functional.spec.Cake;

public class BaseCake implements Cake {
    @Override
    public String decorate() {
        return "Base Cake";
    }
}

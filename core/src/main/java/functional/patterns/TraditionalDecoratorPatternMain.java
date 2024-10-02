package functional.patterns;

import functional.model.BaseCake;
import functional.model.CreamDecorator;
import functional.model.NutsDecorator;
import functional.spec.Cake;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TraditionalDecoratorPatternMain {

    public static void main(String[] args) {
        Cake cake = new NutsDecorator(new CreamDecorator(new BaseCake()));
        log.info("Cake = {}", cake.decorate());
    }
}

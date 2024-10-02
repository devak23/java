package functional.model;

import functional.spec.Cake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CakeDecorator implements Cake {
    private final Cake cake;

    @Override
    public String decorate() {
        return cake.decorate();
    }
}

package functional;

import functional.spec.RemovalStrategy;

public final class Remover {

    private Remover() {}

    public static String remove(String source, RemovalStrategy strategy) {
        return strategy.applyRemoval(source);
    }
}

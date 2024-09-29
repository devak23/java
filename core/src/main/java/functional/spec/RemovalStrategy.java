package functional.spec;

@FunctionalInterface
public interface RemovalStrategy {
    String applyRemoval(String source);
}

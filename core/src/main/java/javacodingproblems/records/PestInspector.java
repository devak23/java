package javacodingproblems.records;

public interface PestInspector {
    default boolean isInfected() {
        return Math.random() > 0.5d;
    }

    void exterminatePest();
}

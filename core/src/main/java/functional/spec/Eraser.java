package functional.spec;

// This is a functional interface. Therefore, this is going to be used as a parameter to a function. In other words,
// the client of this functional interface will define a lambda on what needs to be done as a part of the
// changeAll implementation.
@FunctionalInterface
public interface Eraser<String> {
    String eraseAll(String s);
}

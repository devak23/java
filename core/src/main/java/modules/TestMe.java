package modules;

public class TestMe {

    public static void main(String[] args) {
        var i = Integer.parseInt("Fail");
    }
}

/*
 * Look at the stacktrace generated. We can clearly see that the format of the stack trace has changed somewhat from
 * the form that was used in Java 8. In particular, the stack frames are now qualified by a module name (java.base)
 * as well as a package name, class name, and line number. This clearly shows that the modular nature of the platform
 * is pervasive and is present for even the simplest program.
 */
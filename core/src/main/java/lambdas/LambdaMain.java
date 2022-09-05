package lambdas;

import java.security.PrivilegedAction;
import java.util.concurrent.Callable;

public class LambdaMain {

    public static void main(String[] args) {
        Callable<Integer> c = () -> 42;
        PrivilegedAction<Integer> pv = () -> 42;

        
    }
}

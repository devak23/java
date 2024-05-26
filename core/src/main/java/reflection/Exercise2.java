package reflection;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Future;

public class Exercise2 {

    /**
     * Returns all the interfaces that the current input class implements.
     * Note: If the input is an interface itself, the method returns all the interfaces the
     * input interface extends.
     */
    public static Set<Class<?>> findAllImplementedInterfaces(Class<?> input) {
        Set<Class<?>> allImplementedInterfaces = new HashSet<>();

        Class<?>[] inputInterfaces = input.getInterfaces();
        for (Class<?> currentInterface : inputInterfaces) {
            allImplementedInterfaces.add(currentInterface);
            allImplementedInterfaces.addAll(findAllImplementedInterfaces(currentInterface));
        }

        return allImplementedInterfaces;
    }

    public static void main(String[] args) {
        Set<Class<?>> classes1 = findAllImplementedInterfaces(HashSet.class);
        System.out.println(classes1);
        Set<Class<?>> classes2 = findAllImplementedInterfaces(HashMap.class);
        System.out.println(classes2);
        Set<Class<?>> classes3 = findAllImplementedInterfaces(Object.class);
        System.out.println(classes3);
        Set<Class<?>> classes4 = findAllImplementedInterfaces(Thread.class);
        System.out.println(classes4);
        Set<Class<?>> classes5 = findAllImplementedInterfaces(Socket.class);
        System.out.println(classes5);
    }
}
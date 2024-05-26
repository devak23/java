package reflection.constructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("----- Person -------");
        printConstructors(Person.class);
        System.out.println("----- Address -------");
        printConstructors(Address.class);

        Person p2 = createInstanceWithArguments(Person.class);
        System.out.println(p2);

        Person abhay = createInstanceWithArguments(Person.class, "Abhay", 45, "Powai, Mumbai");
        System.out.println(abhay);

        Person junk = createInstanceWithArguments(Person.class, null, null, null, null, null, null);
        System.out.println(junk);
    }

    public static void printConstructors(Class<?> clazz) {
        Constructor<?> [] constructors = clazz.getConstructors();
        System.out.printf("Class %s has %d declared constructors\n", clazz.getSimpleName(), constructors.length);

        System.out.println("Printing parameterTypeNames...");
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            List<String> parameterTypeNames = Arrays.stream(parameterTypes).map(Class::getSimpleName).collect(Collectors.toList());

            System.out.println(parameterTypeNames);
        }
    }


    public static <T> T createInstanceWithArguments(Class<T> clazz, Object ...args) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == args.length) {
                try {
                    return (T) constructor.newInstance(args);
                } catch (Exception e) {
                    System.out.printf("Problem creating an instance of the class desired:%s\n", e.getMessage());
                    return null;
                }
            }
        }

        System.out.println("An appropriate constructor was not found. Returning null...");
        return null;
    }
}

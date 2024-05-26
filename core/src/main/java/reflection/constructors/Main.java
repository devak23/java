package reflection.constructors;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("----- Person -------");
        printConstructors(Person.class);
        System.out.println("----- Address -------");
        printConstructors(Address.class);
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
}

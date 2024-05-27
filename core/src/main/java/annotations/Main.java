package annotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Cat cat = new Cat("Mimi", 5);

        if (cat.getClass().isAnnotationPresent(VeryImportant.class)) {
            System.out.println("This thing is very important");
        } else {
            System.out.println("Not important at all!");
        }

        for (Method method : cat.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(RunImmediately.class)) {
                int times = method.getAnnotation(RunImmediately.class).times();
                for (int i = 0; i < times; i++) {
                    method.invoke(cat);
                }
            }
        }

        for (Field f : cat.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(ImpParam.class)) {
                System.out.println("Important! ~>" + f.get(cat).toString().toUpperCase());
            }
        }
    }
}

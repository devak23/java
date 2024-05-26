package reflection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<String> stringClass = String.class;

        Map<String, Integer> mapObject = new HashMap<>();

        Class<?> hashmapClass = mapObject.getClass();

        Class<?> squareClass = Class.forName("reflection.Main$Square");

//        printClassInfo(stringClass, hashmapClass, squareClass, Boolean.class);
        var circle = new Drawable() {
            @Override
            public int getNumberOfCorners() {
                return 0;
            }
        };

        printClassInfo(Collection.class, boolean.class, int [][].class, Color.class, circle.getClass());
    }



    private static void printClassInfo(Class<?> ... classes) {

        for (Class<?> clazz : classes) {
            System.out.printf("class name: %s, class package name: %s%n",
                    clazz.getSimpleName(),
                    clazz.getPackageName());

            Class<?> [] implementedInterfaces = clazz.getInterfaces();

            for (Class<?> implementedInterface : implementedInterfaces) {
                System.out.printf("class %s implements: %s%n",
                        clazz.getSimpleName(),
                        implementedInterface.getSimpleName());
            }

            System.out.println("Is array: " + clazz.isArray());
            System.out.println("Is primitive: " + clazz.isPrimitive());
            System.out.println("Is enum: " + clazz.isEnum());
            System.out.println("Is interface " + clazz.isInterface());
            System.out.println("Is anonymous " + clazz.isAnonymousClass());

            System.out.println();
            System.out.println();
        }
    }

    private static class Square implements Drawable {

        @Override
        public int getNumberOfCorners() {
            return 4;
        }
    }

    private static interface Drawable {
        int getNumberOfCorners();
    }

    private enum Color {
        RED,
        BLUE,
        GREEN
    }
}

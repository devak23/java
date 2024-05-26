package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Exercise3 {

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        ImageBuffer imageBuffer = createObject(ImageBuffer.class, 200);
        System.out.println(imageBuffer);
    }

    public static <T> T createObject(Class<T> clazz, Object ...args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Class<?>[] parameterTypes = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);
        Constructor<?> constructor = clazz.getConstructor(parameterTypes);

        return (T) constructor.newInstance(args);
    }
}

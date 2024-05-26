package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Exercise3 {

    public static final byte[] BYTES = new byte[200];

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        ImageBuffer imageBuffer1 = createObject(ImageBuffer.class, 200);
        System.out.println(imageBuffer1);

        ImageBuffer imageBuffer2 = createObject(ImageBuffer.class, BYTES);
        System.out.println(imageBuffer2);

        ImageBuffer imageBuffer3 = createObject(ImageBuffer.class);
        System.out.println(imageBuffer3);

    }

    public static <T> T createObject(Class<T> clazz, Object ...args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Class<?>[] parameterTypes = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);
        Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
        constructor.setAccessible(true);
        return (T) constructor.newInstance(args);
    }
}

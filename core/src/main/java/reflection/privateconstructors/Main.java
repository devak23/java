package reflection.privateconstructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        initConfiguration();
        WebServer server = new WebServer();
        server.startServer();
    }

    public static void initConfiguration() {
        try {
            Constructor<ServerConfiguration> constructor = ServerConfiguration.class.getDeclaredConstructor(int.class, String.class);
            constructor.setAccessible(true);
            constructor.newInstance(8000, "Good Day!");
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

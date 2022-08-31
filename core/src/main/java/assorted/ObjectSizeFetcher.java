package assorted;

import java.lang.instrument.Instrumentation;

// To determine the size of the object.
// this class is compiled and a jar is created out of it.
public class ObjectSizeFetcher {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }
}

package assorted;

public class NoClassDefTest {

    private static class BadInit {
        private static int thisIsFine = 1 / 0;
    }

    public static void main(String[] args) {
        try {
            var init = new BadInit();
        } catch (Throwable t) {
            System.out.printf("ERROR: %s\n", t);
        }

        var init2 = new BadInit();
        System.out.println("~>: " + init2.thisIsFine);
    }
}

/*
 * OUTPUT:
 * ERROR: java.lang.ExceptionInInitializerError
 * Exception in thread "main" java.lang.NoClassDefFoundError: Could not initialize class assorted.NoClassDefTest$BadInit
 * 	at assorted.NoClassDefTest.main(NoClassDefTest.java:16)
 * Caused by: java.lang.ExceptionInInitializerError: Exception java.lang.ArithmeticException: / by zero [in thread "main"]
 * 	at assorted.NoClassDefTest$BadInit.<clinit>(NoClassDefTest.java:6)
 * 	at assorted.NoClassDefTest.main(NoClassDefTest.java:11)
 */


/*
 * This shows that the JVM tried to load the BadInit class but failed to do so. Nevertheless, the program caught the
 * exception and tried to carry on. When the class was encountered for the second time, however, the JVM’s internal
 * metadata table showed that the class had been seen but that a valid class was not loaded.The JVM effectively
 * implements negative caching on a failed class loading attempt the loading is not retried, and instead an error
 * (NoClassDefFoundError) is thrown.
 */

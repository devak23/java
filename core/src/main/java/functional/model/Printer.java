package functional.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Printer {
    private static final Logger log = LoggerFactory.getLogger(Print.class);

    Printer() {
        log.info("Constructor: Starting Printer...");
    }

    public static void printNoReset() {
        log.info("Printing (no reset)...: {}", Printer.class.hashCode());
    }

    public void printReset() {
        log.info("Printing (with reset)...: {}", Printer.class.hashCode());
    }
}

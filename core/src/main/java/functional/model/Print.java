package functional.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Print {

    public void start() {

        // Scenario 2
         Runnable p1 = Printer::printNoReset; // method reference
         Runnable p2 = () -> Printer.printNoReset(); // lambda

         // Scenario 1
//        Runnable p1 = new Printer()::printReset; // method reference
//        Runnable p2 = () -> new Printer().printReset(); // lambda

        log.info("p1:");p1.run();
        log.info("p1:");p1.run();
        log.info("p2:");p2.run();
        log.info("p2:");p2.run();
        log.info("p1:");p1.run();
        log.info("p2:");p2.run();
    }
}

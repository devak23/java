package dates;

import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

@Slf4j
public class DatesMain {
    public static BiFunction<Date, ZoneId, String> toDaysPeriod = Dates::toDayPeriod;
    public static BiFunction<Date, ZoneId, String> getToDaysPeriodNewPattern = Dates::toDayPeriodWithNewPattern;

    public static void main(String[] args) {
        printDates(toDaysPeriod);
        printDates(getToDaysPeriodNewPattern);
    }

    public static void printDates(BiFunction<Date, ZoneId, String> func) {
        log.info("====================================================");
        List<String> zoneIds = List.of("America/Los_Angeles", "America/Montreal", "Africa/Casablanca", "Europe/Berlin","Asia/Kolkata", "Japan", "Australia/Sydney");
        Date now = new Date();
        zoneIds.forEach(z -> log.info("{}:  {}",z, func.apply(now ,ZoneId.of(z))));
    }
}

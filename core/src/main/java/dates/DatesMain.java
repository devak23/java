package dates;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

@Slf4j
public class DatesMain {

    public static void main(String[] args) {
        Date now = new Date();

        printDateInfo(Dates::toDayPeriod);
        printDateInfo(Dates::toDayPeriodWithNewPattern);
        YearMonth yearMonth = Dates.toYearMonth(now, ZoneId.systemDefault());
        log.info("YearMonth = {}", yearMonth);
        log.info("Date = {}", Dates.toDate(21, yearMonth, ZoneId.of("Asia/Kolkata")));
        log.info("Int from YearMonth = {}", Dates.to(YearMonth.now()));
        log.info("Date from week and year: {}", Dates.from(2024, 20));
        log.info("LocalDate from week and year: {}", Dates.fromLocal(2024, 20));
        DateElements elements = Dates.getElements(now);
        log.info("Elements of a date =  Year: {}, Month: {}, Day: {}, Hours: {}, Minutes: {}, Seconds: {}"
                , elements.year(), elements.month(), elements.day(), elements.hour(), elements.minute(), elements.second()
        );
        log.info("util.date = {}, LocalDate = {}", now, Dates.from(now));
        log.info("Fields from LocalDate: {}", Dates.getFieldsFromLocalDate(LocalDate.now()));
        log.info("Is 300 leap year: {}", Dates.isLeapYear(300));
        log.info("Is 400 leap year: {}", Dates.isLeapYear(400));
        log.info("Is 600 leap year: {}", Dates.isLeapYear(400));
        log.info("Is 2020 leap year: {}", Dates.isLeapYear(2020));
        log.info("Is 2030 leap year: {}", Dates.isLeapYear(2030));
        log.info("Is 2024 leap year: {}", Dates.isLeapYearGregorian(2024));
        log.info("Is 2028 leap year: {}", Dates.isLeapYearDefault(2028));
        Quarter quarterDays = Dates.getQuarterDays(createDate(2024, 9, 1));
        log.info("First and last day of the quarter of 1-Sep-2024 are: {}, {}", quarterDays.firstDay(), quarterDays.lastDay());
    }

    public static Date createDate(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static void printDateInfo(BiFunction<Date, ZoneId, String> func) {
        log.info("====================================================");
        List<String> zoneIds = List.of("America/Los_Angeles", "America/Montreal", "Africa/Casablanca"
                , "Europe/Berlin","Asia/Kolkata", "Japan", "Australia/Sydney");
        zoneIds.forEach(z -> log.info("{}:  {}",z, func.apply(new Date(), ZoneId.of(z))));
    }
}

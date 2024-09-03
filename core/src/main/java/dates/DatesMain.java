package dates;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormatSymbols;
import java.time.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

import static java.time.Clock.system;

@Slf4j
public class DatesMain {

    public static void main(String[] args) throws InterruptedException {
        Date now = new Date();

        printDateInfo(Dates::toDayPeriod);
        printDateInfo(Dates::toDayPeriodWithNewPattern);

        YearMonth yearMonth = Dates.toYearMonth(now, ZoneId.systemDefault());
        log.info("YearMonth = {}", yearMonth);
        log.info("Date = {}", Dates.toDate(21, yearMonth, ZoneId.of("America/Montreal")));
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

        Date date = createDate(2024, 9, 1, ZoneId.systemDefault());
        Quarter quarterDays = Dates.getQuarterDays(date);
        log.info("First and last day of the quarter of 1-Sep-2024 are: {}, {}", quarterDays.firstDay(), quarterDays.lastDay());
        log.info("Months of a quarter for date: {} = {}", date, Dates.getMonthsOfQuarter(date));
        log.info("Months of the 3rd quarter: {}", Dates.getMonthsOfQuarter(3));

        NanoStopwatch stopwatch = new NanoStopwatch();
        stopwatch.start();
        log.info("Elapsed time in nanoseconds =  {}", stopwatch.timeElapsedInNanoSeconds());
        //TimeUnit.SECONDS.sleep(1);
        stopwatch.stop();
        log.info("Elapsed time in milliseconds =  {}", stopwatch.timeElapsedInMilliseconds());
        LocalDateTime ld1 = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime ld2 = LocalDateTime.of(2024, 7, 1, 0, 0, 0);
        log.info("Equal split between dates: {}", Dates.splitInEqualIntervals(ld1, ld2, 3));

        log.info("Clock.systemDefaultZone(): {}", Clock.systemDefaultZone());
        log.info("system(ZoneId.systemDefault()): {}", system(ZoneId.systemDefault()));
        log.info("system instant: {}", Clock.systemDefaultZone().instant());
        log.info("UTC instant: {}", Clock.systemUTC().instant());
        log.info("LocalDateTime from UTC: {}", LocalDateTime.now(Clock.systemUTC()));
        log.info("LocalDateTime from Default: {}", LocalDateTime.now(Clock.systemDefaultZone()));

        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        log.info("Days Of the week: {}", Arrays.stream(dateFormatSymbols.getWeekdays()).filter(StringUtils::isNotBlank).toList());
        log.info("Short days of the week: {}", Arrays.stream(dateFormatSymbols.getShortWeekdays()).filter(StringUtils::isNotBlank).toList());
        log.info("Months of a year: {}", Arrays.stream(dateFormatSymbols.getMonths()).filter(StringUtils::isNotBlank).toList());
        log.info("Short months of a year: {}", Arrays.stream(dateFormatSymbols.getShortMonths()).filter(StringUtils::isNotBlank).toList());
}


    public static Date createDate(int year, int month, int day, ZoneId zoneId) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    public static void printDateInfo(BiFunction<Date, ZoneId, String> func) {
        log.info("====================================================");
        List<String> zoneIds = List.of("America/Los_Angeles", "America/Montreal", "Africa/Casablanca"
                , "Europe/Berlin","Asia/Kolkata", "Japan", "Australia/Sydney");
        zoneIds.forEach(z -> log.info("{}:  {}",z, func.apply(new Date(), ZoneId.of(z))));
    }
}

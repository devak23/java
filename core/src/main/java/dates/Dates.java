package dates;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class Dates {

    public static String toDayPeriod(Date date, ZoneId zoneId) {
        LocalTime lt = date.toInstant().atZone(zoneId).toLocalTime();

        LocalTime night = LocalTime.of(21, 0, 0); // 9:00 PM
        LocalTime morning = LocalTime.of(6, 0, 0); // 6:00 AM
        LocalTime afternoon = LocalTime.of(12, 0, 0); // 12:00
        LocalTime evening = LocalTime.of(18, 0, 0); // 6:00 PM
        LocalTime almostMidnight = LocalTime.of(23, 59, 59); // 11:59 PM
        LocalTime midnight = LocalTime.of(0, 0, 0); // 00:00

        if ((lt.isAfter(night) && lt.isBefore(almostMidnight))
        || (lt.isAfter(midnight) && lt.isBefore(morning))) {
            return "night";
        } else if (lt.isAfter(morning) && lt.isBefore(afternoon)) {
            return "morning";
        } else if (lt.isAfter(afternoon) && lt.isBefore(evening)) {
            return "afternoon";
        } else if (lt.isAfter(evening) && lt.isBefore(night)) {
            return "evening";
        }


        return "day";
    }

    public static String toDayPeriodWithNewPattern(Date date, ZoneId zoneId) {
        ZonedDateTime zdt = date.toInstant().atZone(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd [B]");
        return zdt
                .withZoneSameInstant(zoneId)
                .format(formatter);
    }

    // Converting date to YearMonth
    public static YearMonth toYearMonth(Date date, ZoneId zoneId) {
        return YearMonth.from(
                date
                .toInstant()
                .atZone(zoneId)
                .toLocalDate()
        );
    }

    // Converting YearMonth to date
    public static Date toDate(int dayOfTheMonth, YearMonth yearMonth, ZoneId zoneId) {
        return Date.from(
                yearMonth
                .atDay(dayOfTheMonth)
                .atStartOfDay(zoneId)
                .toInstant()
        );
    }

    // converting between int and YearMonth
    public static int to(YearMonth ym) {
        // Consider that we have YearMonth.now() and we want to convert it to an integer (for example, this can
        // be useful for storing a year/month date in a database using a numeric field)
        return (int) ym.getLong(ChronoField.PROLEPTIC_MONTH);
        // The proleptic month is a java.time.temporal.TemporalField, which basically represents a date-time
        // field such as month-of-year (our case) or minute-of-hour. The proleptic-month starts from 0 and counts
        // the months sequentially from year 0. So, getLong() returns the value of the specified field (here, the
        // proleptic-month) from this year-month as a long. We can cast this long to int since the proleptic-month
        // shouldn’t go beyond the int domain (for instance, for 2023/2 the returned int is 24277).
    }

    // Converting week/year to Date
    public static Date from(int year, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, 1);

        return calendar.getTime();
    }

    // converting week/year to LocalDate
    public static LocalDate fromLocal(int year, int week) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        return LocalDate.now()
                .withYear(year)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), 1);
    }

    // get individual elements from date
    public static DateElements getElements(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new DateElements(calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
                , calendar.get(Calendar.HOUR)
                , calendar.get(Calendar.MINUTE)
                , calendar.get(Calendar.SECOND)
        );
    }

    public static LocalDate from(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}

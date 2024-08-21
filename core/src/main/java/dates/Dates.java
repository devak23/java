package dates;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class Dates {

    public static String toDayPeriod(Date date, ZoneId zoneId) {
        LocalTime lt = date.toInstant().atZone(zoneId).toLocalTime();

        LocalTime night = LocalTime.of(21, 0, 0);
        LocalTime morning = LocalTime.of(6, 0, 0);
        LocalTime afternoon = LocalTime.of(12, 0, 0);
        LocalTime evening = LocalTime.of(18, 0, 0);
        LocalTime almostMidnight = LocalTime.of(23, 59, 59);
        LocalTime midnight = LocalTime.of(0, 0, 0);

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
        return zdt.withZoneSameInstant(zoneId).format(formatter);
    }
}

package Manager;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Service {
    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    private static String getDescription(long days) {
        if (days < 0) return "-";
        else if (days == 0) return "0";
        else return String.valueOf(days);
    }

    public static  String daysBetween(Date dateTo) {
        Date nowDate = new Date();
        long days = ChronoUnit.DAYS.between(LocalDateOf(nowDate), LocalDateOf(dateTo));
        String descriptionInTray = getDescription(days);
        return descriptionInTray;
    }

    private static LocalDate LocalDateOf(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}

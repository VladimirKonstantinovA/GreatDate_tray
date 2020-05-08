package Manager;

import java.awt.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {
    public static void start(TrayIcon trayIcon) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    TrayManager.updateTrayIcon(trayIcon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                start(trayIcon);
            }
        };
        timer.schedule(timerTask, nextTime());
    }

    private static Date nextTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 5);
        Date nextDate = calendar.getTime();
        return nextDate;
    }

}

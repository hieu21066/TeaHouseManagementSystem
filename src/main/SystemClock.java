package main;

import java.time.LocalDateTime;

public class SystemClock {

    public static int getCurrentHour() {
        return LocalDateTime.now().getHour();
    }

    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();

        return String.format("%02d/%02d/%04d %02d:%02d:%02d",
                now.getDayOfMonth(),
                now.getMonthValue(),
                now.getYear(),
                now.getHour(),
                now.getMinute(),
                now.getSecond());
    }
}

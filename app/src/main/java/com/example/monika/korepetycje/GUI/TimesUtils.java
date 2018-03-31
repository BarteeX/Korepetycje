package com.example.monika.korepetycje.GUI;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class TimesUtils {
    static final int SECOND_IN_MILLIS = 1000;

    private static Map<String, Integer> timeToMapSM(Integer minutes, Integer seconds) {
        Map<String, Integer> result = new HashMap<>();
        result.put("seconds", seconds);
        result.put("minutes", minutes);
        return result;
    }

    private static Map<String, Integer> timeToMapHM(Integer hours, Integer minutes) {
        Map<String, Integer> result = new HashMap<>();
        result.put("hours", hours);
        result.put("minutes", minutes);
        return result;
    }

    public static Map<String, Integer> getTime(long time) {
        Integer seconds = (int) TimeUnit.MILLISECONDS.toSeconds(time);
        Integer minutes = 0;
        while (seconds >= 60) {
            seconds -= 60;
            minutes++;
        }
        return timeToMapSM(minutes, seconds);
    }


    static Map<String, Integer> getBetween(String timeFrom, String timeTo) {
        Integer timeFromHours = TimesUtils.getHoursFrom(timeFrom);
        Integer timeFromMinutes = TimesUtils.getMinutesFrom(timeFrom);

        Integer timeToHours = TimesUtils.getHoursFrom(timeTo);
        Integer timeToMinutes = TimesUtils.getMinutesFrom(timeTo);

        int hoursBetween = timeToHours - timeFromHours;
        int minutesBetween = timeToMinutes - timeFromMinutes;
        while (minutesBetween < 0) {
            hoursBetween -= 1;
            minutesBetween += 60;
        }
        return timeToMapHM(hoursBetween, minutesBetween);
    }

    private static Integer getMinutesFrom(@NonNull String time) {
        if(time.matches("/d{1,2}:/d{1,2}")) {
            String[] split = time.trim().split(":");
            return Integer.valueOf(split[1]);
        }
        return -1;
    }

    private static Integer getHoursFrom(String time) {
        if(time.matches("/d{1,2}:/d{1,2}")) {
            String[] split= time.trim().split(":");
            return Integer.valueOf(split[0]);
        }
        return -1;
    }

    static int toMillis(int hours, int minutes) {
        int millisToSet = 1;
        if (hours > 0)
            millisToSet *= hours;
        if (minutes > 0)
            millisToSet *= minutes;

        millisToSet *= 60;
        millisToSet *= SECOND_IN_MILLIS;
        return millisToSet;
    }

    static boolean isTimeCorrect(int hours, int minutes) {
        return hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60;
    }
}

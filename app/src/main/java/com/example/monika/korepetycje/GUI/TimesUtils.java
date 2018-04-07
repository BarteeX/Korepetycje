package com.example.monika.korepetycje.GUI;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class TimesUtils {
    static final int MILLIS_IN_SECOND = 1000;

    /**
     * Method convert hours and minutes
     *
     * @param seconds amount of seconds to convert
     * @param minutes amount of minutes to convert
     * @return {
     *      "minutes" : (Integer),
     *      "seconds" : (Integer)
     *  }
     */
    private static Map<String, Integer> timeToMapSM(Integer minutes, Integer seconds) {
        Map<String, Integer> result = new HashMap<>();
        result.put("seconds", seconds);
        result.put("minutes", minutes);
        return result;
    }

    /**
     * Method convert hours and minutes
     *
     * @param hours amount of hours to convert
     * @param minutes amount of minutes to convert
     * @return {
     *      "hours" : (Integer),
     *      "minutes" : (Integer)
     *  }
     */
    private static Map<String, Integer> timeToMapHM(Integer hours, Integer minutes) {
        Map<String, Integer> result = new HashMap<>();
        result.put("hours", hours);
        result.put("minutes", minutes);
        return result;
    }

    /**
     * Method resolve time [HH:mm] from milliseconds
     *
     * @param time amount of milliseconds to convert
     * @return {
     *      "hours" : (Integer),
     *      "minutes" : (Integer)
     *  }
     */
    public static Map<String, Integer> getTime(long time) {
        Integer seconds = (int) TimeUnit.MILLISECONDS.toSeconds(time);
        Integer minutes = 0;
        while (seconds >= 60) {
            seconds -= 60;
            minutes++;
        }
        return timeToMapSM(minutes, seconds);
    }

    /**
     * Method resolve time gap between two [HH:mm] Strings
     * eg.:
     *  [23:30, 01:30] = {
     *      hours : 22
     *      minutes : 0
     *  }
     *  not {
     *      hours : 2
     *      minutes : 0
     *  }
     *
     * @param time1 [HH:mm] - String format
     * @param time2 [HH:mm] - String format
     * @return {
     *      "hours" : (Integer),
     *      "minutes" : (Integer)
     *  }
     */
    static Map<String, Integer> getBetween(String time1, String time2) {
        Integer timeFromHours = TimesUtils.getHoursFrom(time1);
        Integer timeFromMinutes = TimesUtils.getMinutesFrom(time1);

        Integer timeToHours = TimesUtils.getHoursFrom(time2);
        Integer timeToMinutes = TimesUtils.getMinutesFrom(time2);

        if (timeFromHours > timeToHours
                || (timeFromHours.equals(timeToHours) && timeFromMinutes > timeToMinutes)) {
            Integer tempTimeHours = timeFromHours;
            Integer tempTimeMinutes = timeFromMinutes;
            timeFromHours = timeToHours;
            timeFromMinutes = timeToMinutes;
            timeToHours = tempTimeHours;
            timeToMinutes = tempTimeMinutes;
        }

        int hoursBetween = timeToHours - timeFromHours;
        int minutesBetween = timeToMinutes - timeFromMinutes;
        while (minutesBetween < 0) {
            hoursBetween -= 1;
            minutesBetween += 60;
        }
        return timeToMapHM(hoursBetween, minutesBetween);
    }

    /**
     * Method resolve amount of minutes in string in format [HH:mm]
     *
     * eg. "11:24" = 24
     *
     * @param time String format [HH:mm]
     * @return (Integer) mm
     */
    static Integer getMinutesFrom(@NonNull String time) {
        if(isTimeCorrect(time)) {
            String[] split = time.trim().split(":");
            return Integer.valueOf(split[1]);
        }
        return -1;
    }

    /**
     * Method resolve amount of hours in string in format [HH:mm]
     *
     * eg. "11:24" = 11
     *
     * @param time String format [HH:mm]
     * @return (Integer) HH
     */
    static Integer getHoursFrom(@NonNull String time) {
        if(isTimeCorrect(time)) {
            String[] split= time.trim().split(":");
            return Integer.valueOf(split[0]);
        }
        return -1;
    }

    /**
     * Change amount of hours and minutes to milliseconds
     *
     * @param hours amount of hours to convert
     * @param minutes amount of minutes to convert
     * @return (Integer) amount of milliseconds in [hours:minutes]
     */
    static int toMillis(int hours, int minutes) {
        if (!isTimeCorrect(hours, minutes)) {
            return  -1;
        }

        int minutesToMillis = 0;
        if (hours > 0) {
            minutesToMillis += hours;
            minutesToMillis *= 60;
        }
        if (minutes > 0)
            minutesToMillis += minutes;

        minutesToMillis *= 60;
        minutesToMillis *= MILLIS_IN_SECOND;
        return minutesToMillis;
    }

    /**
     * Method checks is time is from [00:00] to [23:59]
     *
     * @param hours Amount of hours to check
     * @param minutes Amount of minutes to check
     * @return (Boolean) is time between [00:00] to [23:59]
     */
    static boolean isTimeCorrect(int hours, int minutes) {
        return hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60;
    }

    /**
     * Method checks is time is from [00:00] to [23:59] but from String
     *
     * @param time Amount of hours to check
     * @return (Boolean) is time between [00:00] to [23:59]
     */
    static boolean isTimeCorrect(String time) {
        return (time != null && time.matches("(([2][0-3])|([1]\\d)|(\\d)):([0-5]?[0-9])"));
    }
}

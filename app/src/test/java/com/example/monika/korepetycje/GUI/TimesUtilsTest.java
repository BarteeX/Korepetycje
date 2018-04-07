package com.example.monika.korepetycje.GUI;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Monika on 2018-04-01.
 */
public class TimesUtilsTest {
    @Test
    public void testIsTimeCorrect() {
        //good probes
        timeCorrectProbe(0,0, true);
        timeCorrectProbe(10,59, true);
        timeCorrectProbe(0,59, true);
        timeCorrectProbe(23,59, true);

        //bad probes
        timeCorrectProbe(24,59, false);
        timeCorrectProbe(25,59, false);
        timeCorrectProbe(-25,-59, false);
        timeCorrectProbe(-1,-1, false);
    }

    private void timeCorrectProbe(int hours, int minutes, boolean expected) {
        boolean probe = TimesUtils.isTimeCorrect(hours,minutes);
        assertEquals("IsTimeCorrect(" + hours + ", " + minutes + ") :", expected, probe);
    }


    @Test
    public void testToMillis() {
        toMillisProbe(0,0, 0);
        toMillisProbe(0,1, 60 * TimesUtils.MILLIS_IN_SECOND);
        toMillisProbe(0,59, 59 * 60 * TimesUtils.MILLIS_IN_SECOND);
        toMillisProbe(23,0, 23 * 60 * 60 * TimesUtils.MILLIS_IN_SECOND);
        toMillisProbe(1,2, 62 * 60 * TimesUtils.MILLIS_IN_SECOND);
        toMillisProbe(3,9, 189 * 60 * TimesUtils.MILLIS_IN_SECOND);
        toMillisProbe(-7653,9, -1);
        toMillisProbe(3,-9, -1);
        toMillisProbe(3,60, -1);
        toMillisProbe(24,60, -1);
        toMillisProbe(0,60, -1);
    }

    private void toMillisProbe(int hours, int minutes, int expected) {
        int millis = TimesUtils.toMillis(hours,minutes);
        assertEquals("toMillisProbe(" + hours + ", " + minutes + ") :", expected, millis);
    }

    @Test
    public void testGetHoursFrom() {
        getHoursProbe("22:00", 22);
        getHoursProbe("24:00", -1);
        getHoursProbe("23:00", 23);
        getHoursProbe("1:00", 1);
        getHoursProbe("1:60", -1);
        getHoursProbe("1:6", 1);
        getHoursProbe("0:60", -1);
        getHoursProbe("0:30", 0);
        getHoursProbe(null, -1);
        getHoursProbe("dupa", -1);
    }

    private void getHoursProbe(String time, int expected) {
        int hour = TimesUtils.getHoursFrom(time);
        assertEquals("getHoursProbe(" + time + ") :", expected, hour);
    }


    @Test
    public void testGetMinutesFrom() {
        getMinutesProbe("22:11", 11);
        getMinutesProbe("24:22", -1);
        getMinutesProbe("23:33", 33);
        getMinutesProbe("1:1", 1);
        getMinutesProbe("1:60", -1);
        getMinutesProbe("1:6", 6);
        getMinutesProbe("0:60", -1);
        getMinutesProbe("0:30", 30);
        getMinutesProbe(null, -1);
        getMinutesProbe("dupa", -1);
    }

    private void getMinutesProbe(String time, int expected) {
        int hour = TimesUtils.getMinutesFrom(time);
        assertEquals("getMinutesProbe(" + time + ") :", expected, hour);
    }

    @Test
    public void testGetBetween() {
        getBetweenProbe("20:00", "21:00" ,1, 0);
        getBetweenProbe("20:00", "21:10" ,1, 10);
        getBetweenProbe("21:20", "21:10" ,0, 10);
        getBetweenProbe("21:20", "21:10" ,0, 10);
        getBetweenProbe("0:0", "21:10" ,21, 10);
    }

    private void getBetweenProbe(String timeFrom, String timeTo, int hour, int minutes) {
        Map<String, Integer> timeMap = TimesUtils.getBetween(timeFrom, timeTo);
        if(timeMap == null || timeMap.isEmpty() || !timeMap.containsKey("hours") || !timeMap.containsKey("minutes")) {
            assertEquals("BAD MAP in BETWEEN PROBE ", true, false );
        } else {
            assertEquals("getBetweenProbe(" + timeFrom + ", " + timeTo + ") : HOUR", hour, (int) timeMap.get("hours"));
            assertEquals("getBetweenProbe(" + timeFrom + ", " + timeTo + ") : MINUTES", minutes, (int) timeMap.get("minutes"));

        }
    }
}
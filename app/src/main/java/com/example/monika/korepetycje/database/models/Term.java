package com.example.monika.korepetycje.database.models;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.managers.TermManager;

import java.io.Serializable;

/**
 * Created by Monika on 2018-01-21.
 */

public class Term extends DatabaseModel implements Serializable {
    public String day;
    public String timeFrom;
    public String timeTo;
    private long studentId;
    private long addressId;

    public Term(long studentId, long addressId) {
        super(TermManager.getInstance());
        this.setStudentId(studentId);
        this.setAddressId(addressId);
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        String bufferString = this.getDay();
        if (bufferString != null && !bufferString.isEmpty()) {
            sb.append("Dnia : ");
            sb.append(bufferString);
            sb.append(" ");
        }

        bufferString = this.getTimeFrom();
        if (this.getTimeFrom() != null && !bufferString.isEmpty()) {
            if (!sb.toString().isEmpty())
                sb.append(", ");
            sb.append("Godz : ");
            sb.append(bufferString);
        }

        bufferString = this.getTimeTo();
        if (bufferString != null && !bufferString.isEmpty()) {
           if (!sb.toString().isEmpty())
               sb.append(", ");

           sb.append("Czas : ");
           sb.append(bufferString);
        }

        bufferString = sb.toString();
        return bufferString;

    }
}

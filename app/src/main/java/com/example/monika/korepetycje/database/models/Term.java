package com.example.monika.korepetycje.database.models;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.managers.TermManager;

import java.io.Serializable;

/**
 * Created by Monika on 2018-01-21.
 */

public class Term extends DatabaseModel implements Serializable {
    private String day;
    private String time;
    private String length;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public long getStudentId() {
        return studentId;
    }

    private void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getAddressId() {
        return addressId;
    }

    private void setAddressId(long addressId) {
        this.addressId = addressId;
    }
}

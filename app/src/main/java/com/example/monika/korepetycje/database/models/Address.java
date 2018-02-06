package com.example.monika.korepetycje.database.models;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.managers.AddressManager;

import java.io.Serializable;

/**
 * Created by Monika on 2018-01-27.
 */

public class Address extends DatabaseModel implements Serializable{
    private String city;
    private String street;
    private String houseNumber;
    private String flatNumber;
    private long studentId;

    public Address(long studentId) {
        super(AddressManager.getInstance());
        this.setStudentId(studentId);
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public long getStudentId() {
        return studentId;
    }

    private void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public boolean isFlat() {
        return flatNumber != null && !flatNumber.isEmpty();
    }

    public String toString() {
        return getCity() + " " + getStreet() + " " + getHouseNumber() + (isFlat() ? "/" + getFlatNumber() : "");
    }
}

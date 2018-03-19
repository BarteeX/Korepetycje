package com.example.monika.korepetycje.database.models;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.managers.AddressManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Address extends DatabaseModel implements Serializable{
    public String city;
    public String street;
    public String houseNumber;
    public String flatNumber;
    private long studentId;

    private List<Term> terms;

    public Address(long studentId) {
        super(AddressManager.getInstance());
        this.setStudentId(studentId);

        this.terms = new ArrayList<>();
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

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public boolean isFlat() {
        return flatNumber != null && !flatNumber.isEmpty();
    }

    public String toString() {
        return getCity() + " " + getStreet() + " " + getHouseNumber() + (isFlat() ? "/" + getFlatNumber() : "");
    }

    public void addTerm(Term term) {
        this.terms.add(term);
    }

    public List<Term> getTerms() {
        return this.terms;
    }
}

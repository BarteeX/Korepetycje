package com.example.monika.korepetycje.database.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.managers.StudentManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 2018-01-21.
 */

public class Student extends DatabaseModel implements Serializable {
    private String name;
    private String surname;
    private String telephoneNumber;
    private List<Term> terms;
    private List<Address> addresses;

    public Student() {
        super(StudentManager.getInstance());
        terms = new ArrayList<>();
        addresses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public void addLesson(Term lesson) {
        terms.add(lesson);
    }

    public void removeLesson(Term lesson) {
        terms.remove(lesson);
    }

    private String capitalize(String string) {
        String firstLetter = string.substring(0,1);
        String restOfLetters = string.substring(1);
        return firstLetter.toUpperCase() + restOfLetters.toLowerCase();
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }

}

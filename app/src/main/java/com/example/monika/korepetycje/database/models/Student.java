package com.example.monika.korepetycje.database.models;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.StateMode;
import com.example.monika.korepetycje.managers.StudentManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 2018-01-21.
 */

public class Student extends DatabaseModel implements Serializable {
    public String name;
    public String surname;
    public String telephoneNumber;
    private List<Term> terms;
    private List<Address> addresses;

    private StateMode stateMode = StateMode.Normal;
    private boolean toDelete = false;
    private boolean expanded = false;

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

    public boolean hasTerms() {
        return this.terms.size() > 0;
    }

    public boolean hasAddresses() {
        return this.addresses.size() > 0;
    }

    public StateMode getStateMode() {
        return stateMode;
    }

    public void setStateMode(StateMode stateMode) {
        this.stateMode = stateMode;
    }

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}

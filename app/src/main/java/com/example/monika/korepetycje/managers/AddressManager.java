package com.example.monika.korepetycje.managers;

import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;

import java.util.ArrayList;
import java.util.List;


public class AddressManager extends ManagerImpl<Address> {

    private static AddressManager instance = null;

    public static AddressManager getInstance() {
        if (instance == null) {
            instance = new AddressManager();
        }
        return instance;
    }

    private AddressManager() {
        super();
    }

    public List<Address> getAddressesForStudent(Student student) {
        List<Address> addressesList = new ArrayList<>();
        for (Address address : list) {
            long studentId = student.getId();
            long addressId = address.getStudentId();

            if (studentId == addressId) {
                addressesList.add(address);
            }
        }
        return addressesList;
    }

    public List<Long> getIdsForStudent(Student student) {
        List<Long> accepted = new ArrayList<>();
        long studentId = student.getId();
        for (Address address : list) {
            if (address.getStudentId() == studentId) {
                accepted.add(address.getId());
            }
        }
        return accepted;
    }
}

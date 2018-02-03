package com.example.monika.korepetycje.managers;

import android.content.Context;

import com.example.monika.korepetycje.database.LessonDatabaseAdapter;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 2018-01-27.
 */

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
        List<Address> list = new ArrayList<>();
        for (Address address : list) {
            long studentId = student.getId();
            long addressId = address.getStudentId();

            if (studentId == addressId) {
                list.add(address);
            }
        }
        return list;
    }
}

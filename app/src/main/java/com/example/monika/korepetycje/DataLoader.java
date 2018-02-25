package com.example.monika.korepetycje;

import android.content.Context;

import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;
import com.example.monika.korepetycje.managers.AddressManager;
import com.example.monika.korepetycje.managers.StudentManager;
import com.example.monika.korepetycje.managers.TermManager;

import java.util.List;


public class DataLoader {
    private static final DataLoader ourInstance = new DataLoader();

    public static DataLoader getInstance() {
        return ourInstance;
    }

    private DataLoader() {
        //empty impl.
    }

    public void loadData(Context context) {
        StudentManager studentManager = StudentManager.getInstance();
        AddressManager addressManager = AddressManager.getInstance();
        TermManager termManager = TermManager.getInstance();

        studentManager.load();
        addressManager.load();
        termManager.load();

        List<Student> students = studentManager.getAll();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            List<Address> addresses = addressManager.getAddressesForStudent(student);
            List<Term> terms = termManager.getTermsForStudent(student);

            student.setAddresses(addresses);
            student.setTerms(terms);
        }
    }
}

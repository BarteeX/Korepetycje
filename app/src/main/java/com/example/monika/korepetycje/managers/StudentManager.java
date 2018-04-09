package com.example.monika.korepetycje.managers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.monika.korepetycje.database.CRUDAdapters.ReadAdapter;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

import java.util.List;

public class StudentManager extends ManagerImpl<Student> {
    private static StudentManager ourInstance = null;

    public static StudentManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new StudentManager();
        }
        return ourInstance;
    }

    private StudentManager() {
        super();
    }

    public Student getById(Long studentId) {
        if (studentId >= 0) {
            for (Student student : list) {
                if (student.getId() == studentId) {
                    return student;
                }
            }
            return null;
        } else {
            return new Student();
        }
    }

    @Override
    public List<Student> filter(@NonNull String filter, @Nullable long... args) {
        AddressManager addressManager = AddressManager.getInstance();
        TermManager termManager = TermManager.getInstance();

        List<Student> accepted = super.filter(filter, args);
        List<Address> acceptedAddresses = addressManager.filter(filter, args);
        List<Term> acceptedTerms = termManager.filter(filter, args);

        for (Address address : acceptedAddresses) {
            Student student = getById(address.getStudentId());
            if (!accepted.contains(student))
                accepted.add(student);
        }

        for (Term term : acceptedTerms) {
            Student student = getById(term.getStudentId());
            if (!accepted.contains(student))
                accepted.add(student);
        }

        return accepted;
    }

    public Student getReference(Student student) {
        Student referenceStudent = null;
        for (Student seeker : list) {
            if (seeker.getId() == student.getId()) {
                referenceStudent = seeker;
            }
        }
        return referenceStudent;
    }

    public List<Student> getStudents(String query) {
        if (query != null) {
            ReadAdapter adapter = ReadAdapter.getInstance();
            return adapter.getStudentsByQuery(query);
        }
        return null;
    }
}

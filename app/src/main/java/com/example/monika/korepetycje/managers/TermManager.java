package com.example.monika.korepetycje.managers;

import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 2018-01-27.
 */

public class TermManager extends ManagerImpl<Term> {

    private static TermManager instance = null;

    public static TermManager getInstance() {
        if (instance == null) {
            instance = new TermManager();
        }
        return instance;
    }

    private TermManager() {
        super();
    }

    public List<Term> getTermsForStudent(Student student) {
        List<Term> list = new ArrayList<>();

        for (Term term : list) {
            long studentId = student.getId();
            long termId = term.getStudentId();

            if (termId == studentId) {
                list.add(term);
            }
        }

        return list;
    }

    public List<Term> getTermsForAddress(Address address) {
        List<Term> terms = new ArrayList<>();

        for (Term term : list) {
            long termId = term.getId();
            long addressId = address.getId();

            if (termId == addressId) {
                terms.add(term);
            }
        }

        return terms;
    }
}

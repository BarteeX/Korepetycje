package com.example.monika.korepetycje.managers;

import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

import java.util.ArrayList;
import java.util.List;


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
        List<Term> termsList = new ArrayList<>();

        for (Term term : list) {
            long studentId = student.getId();
            long termId = term.getStudentId();

            if (termId == studentId) {
                termsList.add(term);
            }
        }

        return termsList;
    }

    public List<Term> getTermsForAddress(Address address) {
        List<Term> termsList = new ArrayList<>();

        for (Term term : list) {
            long termId = term.getAddressId();
            long addressId = address.getId();

            if (termId == addressId) {
                termsList.add(term);
            }
        }

        return termsList;
    }

    public List<Long> getIdsForStudent(Student student) {
        List<Long> accepted = new ArrayList<>();
        long studentId = student.getId();
        for (Term term : list) {
            if (term.getStudentId() == studentId) {
                accepted.add(term.getId());
            }
        }
        return accepted;
    }
}

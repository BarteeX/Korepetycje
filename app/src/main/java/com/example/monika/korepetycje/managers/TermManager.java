package com.example.monika.korepetycje.managers;

import android.content.Context;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.LessonDatabaseAdapter;
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

    public List<Term> getAddressesForStudent(Student student) {
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
}
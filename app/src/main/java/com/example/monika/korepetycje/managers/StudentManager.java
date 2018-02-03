package com.example.monika.korepetycje.managers;

import android.content.Context;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.LessonDatabaseAdapter;
import com.example.monika.korepetycje.database.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 2018-01-21.
 */

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

    public Student findById(Integer studentId) {
        for (Student student : list) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        return null;
    }
}

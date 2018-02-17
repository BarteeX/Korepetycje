package com.example.monika.korepetycje.managers;

import com.example.monika.korepetycje.database.models.Student;

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
}

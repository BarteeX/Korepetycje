package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.app.Activity;

import com.example.monika.korepetycje.database.models.Student;


public abstract class DefaultStudentListener {
    protected Student student;
    protected Activity context;

    DefaultStudentListener(Student student, Activity context) {
        this.student = student;
        this.context = context;
    }
}

package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.app.Activity;

import com.example.monika.korepetycje.database.models.Student;

/**
 * Created by Monika on 2018-03-17.
 */

public abstract class DefaultListener {
    protected Student student;
    protected Activity context;

    DefaultListener(Student student, Activity context) {
        this.student = student;
        this.context = context;
    }
}

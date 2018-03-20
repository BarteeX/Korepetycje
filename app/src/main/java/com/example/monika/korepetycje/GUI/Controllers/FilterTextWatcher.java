package com.example.monika.korepetycje.GUI.Controllers;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.managers.StudentManager;

import java.util.ArrayList;
import java.util.List;


public class FilterTextWatcher implements TextWatcher {
    private StudentManager studentManager;
    private List<Student> list;
    private StudentsList context;

    public FilterTextWatcher(StudentsList context) {
        this.studentManager = StudentManager.getInstance();
        this.list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String filter = editable.toString();
        if (filter.length() > 3) {
            list.addAll(studentManager.filter(filter));
            context.loadStudentsList(list);
        } else {
            List<Student> studentList = studentManager.getAll();
            if (!studentList.equals(context.getStudents())) {
                list.addAll(studentList);
                context.loadStudentsList(list);
            }
        }
        context.notifyDataSetChanged();
    }
}

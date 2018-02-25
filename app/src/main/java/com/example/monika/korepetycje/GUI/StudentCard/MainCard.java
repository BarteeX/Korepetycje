package com.example.monika.korepetycje.GUI.StudentCard;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Student;


@SuppressLint("ValidFragment")
public class MainCard extends android.support.v4.app.Fragment {

    private Student student = null;

    public MainCard(Student student) {
        super();
        this.student = student;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_card, container, false);
        setData(view);
        return view;
    }

    public void setData(View view) {
        String nameStudent = student.getName();
        String surnameStudent = student.getSurname();
        String telephoneNumberStudent = student.getTelephoneNumber();

        EditText name = view.findViewById(R.id.name);
        EditText surname = view.findViewById(R.id.surname);
        EditText telephoneNumber = view.findViewById(R.id.telephoneNumber);

        name.setText(nameStudent);
        surname.setText(surnameStudent);
        telephoneNumber.setText(telephoneNumberStudent);
    }
}

package com.example.monika.korepetycje.GUI.StudentCard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        setSaveButtonListener(view);
        return view;
    }

    public void setData(View view) {
        String nameStudent = student.getName();
        String surnameStudent = student.getSurname();
        String telephoneNumberStudent = student.getTelephoneNumber();

        EditText name = view.findViewById(R.id.student_name);
        EditText surname = view.findViewById(R.id.student_surname);
        EditText telephoneNumber = view.findViewById(R.id.telephoneNumber);

        name.setText(nameStudent);
        surname.setText(surnameStudent);
        telephoneNumber.setText(telephoneNumberStudent);
    }


    public void setSaveButtonListener(View view) {
        Button saveButton = view.findViewById(R.id.save_student_button);
        if (saveButton != null) {
            saveButton.setOnClickListener((view1) -> {
                EditText name = view.findViewById(R.id.student_name);
                EditText surname = view.findViewById(R.id.student_surname);
                EditText telephoneNumber = view.findViewById(R.id.telephoneNumber);

                student.setName(name.getText().toString());
                student.setSurname(surname.getText().toString());
                student.setTelephoneNumber(telephoneNumber.getText().toString());
                student.setAddresses(student.getAddresses());
                student.setTerms(student.getTerms());
                student.save();
                getActivity().finish();
            });
        } else {
            System.out.println("PROBLEM Z ZAPISEM");
        }
    }
}

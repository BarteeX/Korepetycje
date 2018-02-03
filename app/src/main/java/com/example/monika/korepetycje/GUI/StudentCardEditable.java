package com.example.monika.korepetycje.GUI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.monika.korepetycje.DataLoader;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;
import com.example.monika.korepetycje.managers.StudentManager;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class StudentCardEditable extends AppCompatActivity {
    private Context context;
    //TODO : DO PRZEROBIENIA PO Wlasnym adapterze
    private final List<String> termList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_card_editable);

        context = getApplicationContext();

        setAddButtonListener();
        setSaveButtonListener();

        Intent intent = getIntent();
        Integer studentId =  intent.getIntExtra("studentId", 0);
        this.loadData(studentId);
    }

    private void setCancelButtonListener() {

    }

    private void setAddButtonListener() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, termList);
        ListView termsList = findViewById(R.id.listView);
        termsList.setAdapter(adapter);
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view1 -> {
            final Dialog dialog = new Dialog(StudentCardEditable.this);
            dialog.setContentView(R.layout.date_stutent_item);
            dialog.setTitle(R.string.date);
            dialog.show();

            final Button saveButton = dialog.findViewById(R.id.saveButton);
            saveButton.setOnClickListener( view2 -> {
                // TODO : NAPISAC WŁASNY ADAPTER
                Spinner spinner = dialog.findViewById(R.id.days_array);
                EditText text = dialog.findViewById(R.id.hour);

                String value =
                        spinner.getSelectedItem().toString()
                                + getString(R.string.term_splitter)
                                + text.getText().toString();

                termList.add(value);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            });


            final Button cancelButton = dialog.findViewById(R.id.cancelButton);
            cancelButton.setOnClickListener(view -> dialog.dismiss());
        });
    }


    private void setSaveButtonListener() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener((view) -> {
            EditText name = findViewById(R.id.name);
            EditText surname = findViewById(R.id.surname);
            EditText telephoneNumber = findViewById(R.id.telephoneNumber);
            EditText city = findViewById(R.id.city);
            EditText street = findViewById(R.id.street);
            EditText houseNumber = findViewById(R.id.houseNumber);
            EditText flatNumber = findViewById(R.id.flatNumber);
            ListView terms = findViewById(R.id.listView);
            ListAdapter adapter = terms.getAdapter();

            Student freshStudent = new Student();
            freshStudent.setName(name.getText().toString());
            freshStudent.setSurname(surname.getText().toString());
            freshStudent.setTelephoneNumber(telephoneNumber.getText().toString());
            freshStudent.save(context);

            Address address = new Address(freshStudent.getId());
            address.setCity(city.getText().toString());
            address.setStreet(street.getText().toString());
            address.setHouseNumber(houseNumber.getText().toString());
            address.setFlatNumber(flatNumber.getText().toString());
            address.save(context);

            //TODO: przerobić layou aby pobierał wiele adresów.
            freshStudent.addAddress(address);

            for (int i = 0; i < adapter.getCount(); i++) {
                String value = adapter.getItem(i).toString();
                String[] split = value.split(String.valueOf(R.string.term_splitter));
                String day = split[0];
                String hour = split[0];

                Term term = new Term(freshStudent.getId(), address.getId());
                term.setDay(day);
                term.setTime(hour);
                freshStudent.addLesson(term);
            }

            freshStudent.save(context);
        });
    }

    private void loadData(Integer studentId) {
        StudentManager manager = StudentManager.getInstance();
        Student student = manager.findById(studentId);

        String nameStudent = student.getName();
        String surnameStudent = student.getSurname();
        String telephoneNumberStudent = student.getTelephoneNumber();
        List<Term> termsStudent = student.getTerms();
        List<Address> addressesStudent = student.getAddresses();

        EditText name = findViewById(R.id.name);
        EditText surname = findViewById(R.id.surname);
        EditText telephoneNumber = findViewById(R.id.telephoneNumber);
        EditText city = findViewById(R.id.city);
        EditText street = findViewById(R.id.street);
        EditText houseNumber = findViewById(R.id.houseNumber);
        EditText flatNumber = findViewById(R.id.flatNumber);
        ListView terms = findViewById(R.id.listView);
        Adapter termsAdapter = terms.getAdapter();

        name.setText(nameStudent);
        surname.setText(surnameStudent);
        telephoneNumber.setText(telephoneNumberStudent);

        //TODO : przerobić na wiele adresów
        if (addressesStudent.size() > 0) {
            //for (Address address : addressesStduent) {
                Address address = addressesStudent.get(0);// << tego nie bedzie
                city.setText(address.getCity());
                street.setText(address.getStreet());
                houseNumber.setText(address.getHouseNumber());
                flatNumber.setText(address.getFlatNumber());
            //}
        }

        if (termsStudent.size() > 0) {
            for(Term term : termsStudent) {
                String value =
                        term.getDay()
                                + getString(R.string.term_splitter)
                                + term.getTime();

                termList.add(value);
            }
        }

    }
}

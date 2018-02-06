package com.example.monika.korepetycje.GUI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;
import com.example.monika.korepetycje.managers.StudentManager;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class StudentCardEditable extends AppCompatActivity {

    private Student student = null;
    private List<Term> terms = null;
    private List<Address> addresses = null;

    private AddressArrayAdapter addressArrayAdapter;
    private TermsArrayAdapter termsArrayAdapter;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_card_editable);

        context = getApplicationContext();

        setStudentData();

        setListeners();
    }

    private void setListeners() {
        setAddTermButtonListener();
        setAddAddressButtonListener();

        setSaveButtonListener();
    }

    private void setStudentData() {
        Intent intent = getIntent();
        Integer studentId =  intent.getIntExtra("studentId", 0);
        this.student = loadData(studentId);
        this.addresses = student.getAddresses();
        this.terms = student.getTerms();
    }

    private void setAddAddressButtonListener() {
        GridLayout gridLayout = findViewById(R.id.student_card);
        final Button addButton = gridLayout.findViewById(R.id.new_address_button);
        addButton.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(StudentCardEditable.this);
            dialog.setContentView(R.layout.student_address_card);
            dialog.setTitle("Adres");
            dialog.show();

            final Button saveButton = dialog.findViewById(R.id.save_button);
            saveButton.setOnClickListener(buttonView -> {
                EditText city = dialog.findViewById(R.id.city);
                EditText street = dialog.findViewById(R.id.street);
                EditText houseNumber = dialog.findViewById(R.id.house_number);
                EditText flatNumber = dialog.findViewById(R.id.flat_number);

                String cityString = city.getText().toString();
                String streetString = street.getText().toString();
                String houseNumberString = houseNumber.getText().toString();
                String flatNumberString = flatNumber.getText().toString();

                Address address = new Address(this.student.getId());
                address.setCity(cityString);
                address.setStreet(streetString);
                address.setHouseNumber(houseNumberString);
                address.setFlatNumber(flatNumberString);

                this.addresses.add(address);
                addressArrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            });

            final Button declineButton = dialog.findViewById(R.id.discard_button);
            declineButton.setOnClickListener(discardView -> {
                dialog.dismiss();
            });

        });

    }

    private void setAddTermButtonListener() {
        GridLayout gridLayout = findViewById(R.id.student_card);
        final Button addButton = gridLayout.findViewById(R.id.new_term_button);
        addButton.setOnClickListener(view1 -> {
            final Dialog dialog = new Dialog(StudentCardEditable.this);
            dialog.setContentView(R.layout.student_term_card);
            dialog.setTitle(R.string.date);
            dialog.show();

            Spinner addressesSpinner = dialog.findViewById(R.id.address_for_term);
            List<String> spinnerItems = new ArrayList<>();
            for (int i = 0; i < addresses.size(); i++) {
                Address address = addresses.get(i);
                spinnerItems.add(address.toString());
                address.setIdn(address.toString());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.student_term_spinner_item, R.id.whole_address, spinnerItems);
            addressesSpinner.setAdapter(adapter);
            addressArrayAdapter.notifyDataSetChanged();

            final Button saveButton = dialog.findViewById(R.id.save_term_button);
            saveButton.setOnClickListener( view2 -> {
                Spinner spinnerDays = dialog.findViewById(R.id.days_array);
                EditText textHour = dialog.findViewById(R.id.hour);

                String day = spinnerDays.getSelectedItem().toString();
                String hour = textHour.getText().toString();

                String addressIdn = (String) addressesSpinner.getSelectedItem();
                Address address = null;

                for (Address adr : addresses) {
                    if (adr.getIdn().equals(addressIdn)) {
                        address = adr;
                    }
                }

                if (address != null) {
                    Term term = new Term(this.student.getId(), address.getId());
                    term.setTime(hour);
                    term.setDay(day);

                    terms.add(term);
                    termsArrayAdapter.notifyDataSetChanged();
                } else {
                    //TODO :)

                    System.out.println("---------------TERMS NOT UPLOADED------------------");
                }

                dialog.dismiss();
            });


            final Button cancelButton = dialog.findViewById(R.id.cancelButton);
            cancelButton.setOnClickListener(view -> dialog.dismiss());
        });
    }


    private void setSaveButtonListener() {
        GridLayout gridLayout = findViewById(R.id.student_card);
        Button saveButton = gridLayout.findViewById(R.id.save_button);
        saveButton.setOnClickListener((view) -> {
            EditText name = findViewById(R.id.name);
            EditText surname = findViewById(R.id.surname);
            EditText telephoneNumber = findViewById(R.id.telephoneNumber);

            student.setName(name.getText().toString());
            student.setSurname(surname.getText().toString());
            student.setTelephoneNumber(telephoneNumber.getText().toString());
            student.setAddresses(addresses);
            student.setTerms(terms);
            student.save(context);

            student.save(context);
            this.finish();
        });
    }

    private Student loadData(Integer studentId) {
        StudentManager manager = StudentManager.getInstance();
        Student student = manager.findById(studentId);

        String nameStudent = student.getName();
        String surnameStudent = student.getSurname();
        String telephoneNumberStudent = student.getTelephoneNumber();

        EditText name = findViewById(R.id.name);
        EditText surname = findViewById(R.id.surname);
        EditText telephoneNumber = findViewById(R.id.telephoneNumber);

        name.setText(nameStudent);
        surname.setText(surnameStudent);
        telephoneNumber.setText(telephoneNumberStudent);

        ListView addressesListView = findViewById(R.id.addresses_list);
        ListView termsListView = findViewById(R.id.terms_list);

        addressArrayAdapter
                = new AddressArrayAdapter(this, R.layout.student_address_item_list, student.getAddresses());
        termsArrayAdapter
                = new TermsArrayAdapter(this, R.layout.student_term_list_item, student.getTerms());

        addressesListView.setAdapter(addressArrayAdapter);
        termsListView.setAdapter(termsArrayAdapter);

        addressArrayAdapter.notifyDataSetChanged();
        termsArrayAdapter.notifyDataSetChanged();

        return student;
    }
}

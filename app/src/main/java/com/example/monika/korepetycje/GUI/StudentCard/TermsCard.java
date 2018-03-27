package com.example.monika.korepetycje.GUI.StudentCard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.monika.korepetycje.GUI.ApplicationHelper;
import com.example.monika.korepetycje.GUI.ArrayAdapters.TermsArrayAdapter;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.M)
@SuppressLint("ValidFragment")
public class TermsCard extends android.support.v4.app.Fragment {

    private Student student = null;
    private List<Address> addresses;
    private List<Term> terms;
    private TermsArrayAdapter termsArrayAdapter;

    public TermsCard(Student student) {
        super();
        this.student = student;
        this.addresses = student.getAddresses();
        this.terms = student.getTerms();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.terms_card, container, false);
        setTermsArrayAdapter(view);
        setAddTermButtonListener(view);
        return view;
    }


    private void setTermsArrayAdapter(View view) {
        ListView termsListView = view.findViewById(R.id.terms_list);
        setTermItemListener(termsListView);
        termsArrayAdapter
                = new TermsArrayAdapter(getActivity(), R.layout.student_term_list_item, student.getTerms());
        termsListView.setAdapter(termsArrayAdapter);
        termsArrayAdapter.notifyDataSetChanged();
    }


    public void setTermItemListener(ListView listView) {
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            if (i >= 0 && i < this.terms.size()) {
                Term term = this.terms.get(i);
                showTermDialog(term);
            }
        });
    }


    private void setAddTermButtonListener(View view) {
        final Button addButton = view.findViewById(R.id.new_term_button);
        addButton.setOnClickListener(view1 -> {
            if (this.addresses.size() > 0) {
                showTermDialog(null);
            } else {
                ApplicationHelper.showMessageDialog("Nie możesz podpiąc terminu bez podania adresu.", getActivity());
            }
        });
    }

    public void showTermDialog(Term term) {
        final Dialog dialog = new Dialog(getContext());
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.student_term_spinner_item, R.id.whole_address, spinnerItems);
        addressesSpinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Spinner spinnerDays = dialog.findViewById(R.id.days_array);
        TimePicker timePicker = dialog.findViewById(R.id.hour);
        timePicker.setIs24HourView(true);

        if (term != null) {

            String[] daysName = getResources().getStringArray(R.array.days_array);
            int daySelection = Arrays.asList(daysName).indexOf(term.getDay());
            spinnerDays.setSelection(daySelection);

            String[] split = term.getTime().split(":");
            String hour = split[0];
            String minutes = split[1];

            timePicker.setHour(Integer.valueOf(hour));
            timePicker.setMinute(Integer.valueOf(minutes));


            long addressId = term.getAddressId();
            int position = 0;
            for (int i = 0, addressesSize = addresses.size(); i < addressesSize; i++) {
                Address address = addresses.get(i);
                if (address.getId() == addressId) {
                    position = 0;
                }
            }
            addressesSpinner.setSelection(position);
        }

        final Button saveButton = dialog.findViewById(R.id.save_term_button);
        saveButton.setOnClickListener(view2 -> {
            String day = spinnerDays.getSelectedItem().toString();
            String hour = timePicker.getHour() + ":" + timePicker.getMinute();

            String addressIdn = (String) addressesSpinner.getSelectedItem();
            Address address = null;

            for (int i = 0, addressesSize = addresses.size(); i < addressesSize; i++) {
                Address adr = addresses.get(i);
                if (adr.getIdn().equals(addressIdn)) {
                    address = adr;
                }
            }

            //obsługa jest na samym przycisku
            if (address != null) {
                Term newTerm = (term == null) ? new Term(this.student.getId(), address.getId()) : term;
                newTerm.setTime(hour);
                newTerm.setDay(day);
                if (!this.terms.contains(newTerm)) {
                    address.addTerm(newTerm);
                    terms.add(newTerm);
                }
                termsArrayAdapter.notifyDataSetChanged();
            }

            dialog.dismiss();
        });


        final Button cancelButton = dialog.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view2 -> dialog.dismiss());

    }
}

package com.example.monika.korepetycje.GUI.StudentCard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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


    @SuppressLint("SetTextI18n")
    private void setTermsArrayAdapter(View view) {
        ListView termsListView = view.findViewById(R.id.terms_list);
        setTermItemListener(termsListView);
        termsArrayAdapter
                = new TermsArrayAdapter(getActivity(), R.layout.student_term_list_item, student.getTerms());
        termsListView.setAdapter(termsArrayAdapter);
        termsArrayAdapter.notifyDataSetChanged();
        termsListView.setOnItemLongClickListener((AdapterView<?> adapterView, View view1, int i, long l) -> {
            Term term = terms.get(i);
            @SuppressLint("ResourceType")
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_window_yes_no);
            dialog.setTitle(R.string.detele_ask);
            TextView info = dialog.findViewById(R.id.dialog_yen_no_text);
            info.setText("Usunąć termin : \n" + term.toString());

            Button noButton = dialog.findViewById(R.id.no_button);
            noButton.setOnClickListener(view2 -> dialog.dismiss());

            Button yesButton = dialog.findViewById(R.id.yes_button);
            yesButton.setOnClickListener(view2 -> {
                term.delete();
                terms.remove(term);
                dialog.dismiss();
                termsArrayAdapter.notifyDataSetChanged();
            });

            dialog.show();

            return true;
        });
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

        final TimePicker timePickerFrom = dialog.findViewById(R.id.time_from);
        final TimePicker timePickerTo = dialog.findViewById(R.id.time_to);

        timePickerFrom.setOnTimeChangedListener((timePicker, hours, minutes) -> {
            timePickerTo.setHour(hours);
            timePickerTo.setMinute(minutes);
        });

        timePickerTo.setOnTimeChangedListener((timePicker, i, i1) -> {
            int hoursFrom = timePickerFrom.getHour();
            int minuterFrom = timePickerFrom.getMinute();
            int hoursTo = timePicker.getHour();
            int minutesTo = timePicker.getHour();
            if (hoursTo < hoursFrom) {
                timePicker.setHour(hoursFrom);
                hoursTo = hoursFrom;
            }

            if (minutesTo < minuterFrom && hoursFrom == hoursTo) {
                timePicker.setMinute(minuterFrom);
            }

        });

        timePickerFrom.setIs24HourView(true);
        timePickerTo.setIs24HourView(true);

        if (term != null) {

            String[] daysName = getResources().getStringArray(R.array.days_array);
            int daySelection = Arrays.asList(daysName).indexOf(term.getDay());
            spinnerDays.setSelection(daySelection);

            String[] splitFrom = term.getTimeFrom().split(":");
            String hourFrom = splitFrom[0];
            String minutesFrom = splitFrom[1];

            timePickerFrom.setHour(Integer.valueOf(hourFrom));
            timePickerFrom.setMinute(Integer.valueOf(minutesFrom));

            String[] splitTo = term.getTimeFrom().split(":");
            String hourTo = splitTo[0];
            String minutesTo = splitTo[1];

            timePickerFrom.setHour(Integer.valueOf(hourTo));
            timePickerFrom.setMinute(Integer.valueOf(minutesTo));


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
            String timeFrom = timePickerFrom.getHour() + ":" + timePickerFrom.getMinute();
            String timeTo = timePickerTo.getHour() + ":" + timePickerTo.getMinute();

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
                newTerm.setTimeFrom(timeFrom);
                newTerm.setTimeTo(timeTo);
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

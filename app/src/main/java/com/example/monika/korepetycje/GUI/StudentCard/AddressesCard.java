package com.example.monika.korepetycje.GUI.StudentCard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;

import com.example.monika.korepetycje.GUI.ArrayAdapters.AddressArrayAdapter;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.M)
@SuppressLint("ValidFragment")
public class AddressesCard extends android.support.v4.app.Fragment {

    private Student student = null;
    private List<Address> addresses;
    private List<Term> terms;
    private AddressArrayAdapter addressArrayAdapter;

    public AddressesCard(Student student) {
        super();
        this.student = student;
        this.addresses = student.getAddresses();
        this.terms = student.getTerms();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addresses_card, container, false);
        setAddressArrayAdapter(view);
        setAddAddressButtonListener(view);
        return view;
    }

    private void setAddressArrayAdapter(View view) {
        ListView addressesListView = view.findViewById(R.id.addresses_list);
        addressArrayAdapter
                = new AddressArrayAdapter(getActivity(), R.layout.student_address_item_list, student.getAddresses());

        addressesListView.setAdapter(addressArrayAdapter);
        addressArrayAdapter.notifyDataSetChanged();
    }

    private void setAddAddressButtonListener(View view) {
        final Button addButton = view.findViewById(R.id.new_address_button);
        addButton.setOnClickListener(view1 -> {
            final Dialog dialog = new Dialog(getContext());
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

}

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
        this.addresses = this.student.getAddresses();
        this.terms = this.student.getTerms();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addresses_card, container, false);
        setAddressArrayAdapter(view);
        setAddAddressButtonListener(view);
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setAddressArrayAdapter(View view) {
        ListView addressesListView = view.findViewById(R.id.addresses_list);
        setAddressItemListener(addressesListView);
        addressArrayAdapter
                = new AddressArrayAdapter(getActivity(), R.layout.student_address_item_list, student.getAddresses());
        addressesListView.setAdapter(addressArrayAdapter);
        addressArrayAdapter.notifyDataSetChanged();

        addressesListView.setOnItemLongClickListener((AdapterView<?> adapterView, View view1, int i, long l) -> {
            Address address = addresses.get(i);
            @SuppressLint("ResourceType")
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_window_yes_no);
            dialog.setTitle(R.string.detele_ask);
            TextView info = dialog.findViewById(R.id.dialog_yen_no_text);
            info.setText("Usunąć adres : \n" + address.toString());

            Button noButton = dialog.findViewById(R.id.no_button);
            noButton.setOnClickListener(view2 -> dialog.dismiss());

            Button yesButton = dialog.findViewById(R.id.yes_button);
            yesButton.setOnClickListener(view2 -> {
                address.delete();
                addresses.remove(address);
                dialog.dismiss();
                addressArrayAdapter.notifyDataSetChanged();
            });

            dialog.show();

            return true;
        });
    }

    public void setAddressItemListener(ListView listView) {
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            if (i >= 0 && i < this.addresses.size()) {
                Address address = this.addresses.get(i);
                showAddressDialog(address);
            }
        });
    }

    private void setAddAddressButtonListener(View view) {
        final Button addButton = view.findViewById(R.id.new_address_button);
        addButton.setOnClickListener(view1 -> {
            showAddressDialog(null);
        });

    }

    public void showAddressDialog(Address address) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.student_address_card);
        dialog.setTitle("Adres");
        dialog.show();

        if (address != null) {
            EditText city = dialog.findViewById(R.id.city);
            EditText street = dialog.findViewById(R.id.street);
            EditText houseNumber = dialog.findViewById(R.id.house_number);
            EditText flatNumber = dialog.findViewById(R.id.flat_number);

            city.setText(address.getCity());
            street.setText(address.getStreet());
            houseNumber.setText(address.getHouseNumber());
            flatNumber.setText(address.getFlatNumber());
        }

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

            Address addr = (address == null) ? new Address(this.student.getId()) : address;
            addr.setCity(cityString);
            addr.setStreet(streetString);
            addr.setHouseNumber(houseNumberString);
            addr.setFlatNumber(flatNumberString);

            if(!this.addresses.contains(addr))
                this.addresses.add(addr);

            addressArrayAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        final Button declineButton = dialog.findViewById(R.id.discard_button);
        declineButton.setOnClickListener(discardView -> {
            dialog.dismiss();
        });
    }

}

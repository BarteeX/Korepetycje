package com.example.monika.korepetycje.GUI;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 2018-02-04.
 */

public class AddressArrayAdapter extends ArrayAdapter<Address> {

    private Activity context;
    private List<Address> addresses;

    private int resource;

    public AddressArrayAdapter(@NonNull Activity context, int resource, List<Address> addresses) {
        super(context, resource);

        this.resource = resource;
        this.context = context;
        this.addresses = addresses;
    }

    public static class AddressViewHolder {
        public TextView city;
        public TextView street;
        public TextView houseNumber;
        public TextView flatNumber;
    }


    @NonNull
    @Override
    public View getView(int position, View currentView, @NonNull ViewGroup parent) {
        return listView(currentView, position);
    }


    private View listView(View currentView, int position) {
        AddressViewHolder addressViewHolder;
        View rowView = currentView;

        Address address = addresses.get(position);

        if (rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(resource, null, true);

            addressViewHolder = new AddressViewHolder();
            addressViewHolder.city = rowView.findViewById(R.id.city);
            addressViewHolder.street = rowView.findViewById(R.id.street);
            addressViewHolder.houseNumber = rowView.findViewById(R.id.house_number);
            addressViewHolder.flatNumber = rowView.findViewById(R.id.flat_number);
            rowView.setTag(addressViewHolder);
        } else {
            addressViewHolder = (AddressViewHolder) rowView.getTag();
        }


        addressViewHolder.city.setText(address.getCity());
        addressViewHolder.street.setText(address.getStreet());
        addressViewHolder.houseNumber.setText(address.getHouseNumber());
        addressViewHolder.flatNumber.setText(address.getFlatNumber());

        return rowView;
    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    public List<Address> getAddresses() {
        return this.addresses;
    }
}

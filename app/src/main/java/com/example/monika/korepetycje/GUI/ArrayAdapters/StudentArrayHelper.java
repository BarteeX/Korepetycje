package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.StateMode;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;


public class StudentArrayHelper {
    public static boolean onMapItemSelection(Address address, Activity context) {
        String url = "https://www.google.com/" +
                "maps/" +
                "search/" +
                "?api=1" +
                "&query=" +
                address.getCity() +
                "+" +
                address.getStreet() +
                "+" +
                address.getHouseNumber() +
                "+" +
                address.getFlatNumber();
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
        return true;
    }

    public static View getConvertView(Student student, Activity context) {
        LayoutInflater layoutInflater = context.getLayoutInflater();

        @SuppressLint("InflateParams")
        View convertView = layoutInflater.inflate(R.layout.student_list_item, null, true);

        StudentsArrayAdapter.StudentViewHolder studentViewHolder = new StudentsArrayAdapter.StudentViewHolder();
        studentViewHolder.name = convertView.findViewById(R.id.name);
        studentViewHolder.surname = convertView.findViewById(R.id.surname);

        studentViewHolder.name.setText(student.getName());
        studentViewHolder.surname.setText(student.getSurname());

        convertView.setTag(studentViewHolder);

        return convertView;
    }

    @SuppressLint("SetTextI18n")
    public static void setButtonsListeners(Student student, Activity context, View convertView, int position) {

        Button messageMutton = convertView.findViewById(R.id.message_button);
        messageMutton.setOnClickListener(new StudentArrayListenerHolder.ButtonMessageListener(student, context));

        Button callButton = convertView.findViewById(R.id.call_button);
        callButton.setOnClickListener(new StudentArrayListenerHolder.CallButtonListener(student, context));

        Button editButton = convertView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new StudentArrayListenerHolder.EditButtonListener(student, context));

        Button mapButton = convertView.findViewById(R.id.map_button);
        mapButton.setOnClickListener(new StudentArrayListenerHolder.MapButtonListener(student, context));

        TextView textView = convertView.findViewById(R.id.student_label);
        textView.setText(context.getString(R.string.student_label_text) + (position + 1));

        RadioButton radioButton = convertView.findViewById(R.id.delete_radio_button);
        radioButton.setVisibility(View.INVISIBLE);
        if (student.getStateMode() == StateMode.Delete) {
            radioButton.setVisibility(View.VISIBLE);
        }
        radioButton.setOnClickListener(new StudentArrayListenerHolder.DeleteRatioButtonListener(student, context));
    }
}

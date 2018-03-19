package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
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
        View convertView = layoutInflater.inflate(R.layout.students_list_item, null, true);

        StudentsArrayAdapter.StudentViewHolder studentViewHolder = new StudentsArrayAdapter.StudentViewHolder();
        studentViewHolder.name = convertView.findViewById(R.id.name);
        studentViewHolder.surname = convertView.findViewById(R.id.surname);

        studentViewHolder.name.setText(student.getName());
        studentViewHolder.surname.setText(student.getSurname());

        convertView.setTag(studentViewHolder);

        return convertView;
    }

    @SuppressLint("SetTextI18n")
    public static void setStudentLabel(View convertView, int position) {
        TextView textView = convertView.findViewById(R.id.student_label);
        textView.setText((position + 1) + "");
    }

    @SuppressLint("SetTextI18n")
    public static void setButtonsListeners(Student student, Activity context, View convertView) {

        setMessageButtonListener(student, context, convertView);
        setCallButtonListener(student, context, convertView);
        setEditButtonListener(student, context, convertView);
        setMapButtonListener(student, context, convertView);
        setDeleteButtonListener(student, context, convertView);
        setExpandButtonListener(student, context, convertView);
    }

    private static void setMessageButtonListener(Student student, Activity context, View convertView) {
        Button messageMutton = convertView.findViewById(R.id.message_button);
        messageMutton.setOnClickListener(new StudentArrayListenerHolder.ButtonMessageListener(student, context));
    }

    private static void setCallButtonListener(Student student, Activity context, View convertView) {
        Button callButton = convertView.findViewById(R.id.call_button);
        callButton.setOnClickListener(new StudentArrayListenerHolder.CallButtonListener(student, context));
    }

    private static void setEditButtonListener(Student student, Activity context, View convertView) {
        Button editButton = convertView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new StudentArrayListenerHolder.EditButtonListener(student, context));
    }

    private static void setMapButtonListener(Student student, Activity context, View convertView) {
        Button mapButton = convertView.findViewById(R.id.map_button);
        mapButton.setOnClickListener(new StudentArrayListenerHolder.MapButtonListener(student, context));
    }

    private static void setDeleteButtonListener(Student student, Activity context, View convertView) {
        RadioButton radioButton = convertView.findViewById(R.id.delete_radio_button);

        if (student.getStateMode().equals(StateMode.Delete)) {
            radioButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            );
        } else {
            radioButton.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    0)
            );
        }
        radioButton.setOnClickListener(new StudentArrayListenerHolder.DeleteRatioButtonListener(student, context));
    }

    private static void setExpandButtonListener(Student student, Activity context, View convertView) {
        TextView expand = convertView.findViewById(R.id.expand_button);
        GridLayout gridLayout = convertView.findViewById(R.id.expander);
        if (student.isExpanded()) {
            expand.setText(R.string.collapse);
            gridLayout.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
        } else {
            expand.setText(R.string.expand);
            gridLayout.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        expand.setOnClickListener(new StudentArrayListenerHolder.ExpandButtonListener(student, context, expand, convertView));
    }
}

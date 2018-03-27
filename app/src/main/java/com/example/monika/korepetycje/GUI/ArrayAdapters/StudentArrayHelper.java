package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.StateMode;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;


class StudentArrayHelper {
    static boolean onMapItemSelection(Address address, Activity context) {
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

    static View getConvertView(Student student, Activity context) {
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
    static void setStudentLabel(View convertView, int position) {
        TextView textView = convertView.findViewById(R.id.student_label);
        textView.setText((position + 1) + "");
    }

    @SuppressLint("SetTextI18n")
    static void setButtonsListeners(Student student, Activity context, View convertView) {

        setMessageButtonListener(student, context, convertView);
        setCallButtonListener(student, context, convertView);
        setEditButtonListener(student, context, convertView);
        setMapButtonListener(student, context, convertView);
        setDeleteButtonListener(student, context, convertView);
        setExpandButtonListener(student, context, convertView);
    }

    private static void setMessageButtonListener(Student student, Activity context, View convertView) {
        Button messageMutton = convertView.findViewById(R.id.message_button);
        messageMutton.setOnClickListener(new StudentArrayListenerHolder.ButtonMessageStudentListener(student, context));
    }

    private static void setCallButtonListener(Student student, Activity context, View convertView) {
        Button callButton = convertView.findViewById(R.id.call_button);
        callButton.setOnClickListener(new StudentArrayListenerHolder.CallButtonStudentListener(student, context));
    }

    private static void setEditButtonListener(Student student, Activity context, View convertView) {
        Button editButton = convertView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new StudentArrayListenerHolder.EditButtonStudentListener(student, context));
    }

    private static void setMapButtonListener(Student student, Activity context, View convertView) {
        Button mapButton = convertView.findViewById(R.id.map_button);
        mapButton.setOnClickListener(new StudentArrayListenerHolder.MapButtonStudentListener(student, context));
    }

    private static void setDeleteButtonListener(Student student, Activity context, View convertView) {
        boolean isDeleteStateMode = student.getStateMode().equals(StateMode.Delete);
        RadioButton radioButton = convertView.findViewById(R.id.delete_radio_button);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
            isDeleteStateMode ? LinearLayout.LayoutParams.MATCH_PARENT : 0,
            isDeleteStateMode ? LinearLayout.LayoutParams.WRAP_CONTENT : 0
        );

        radioButton.setLayoutParams(layoutParams);
        radioButton.setChecked(student.isToDelete());
        radioButton.setOnClickListener(new StudentArrayListenerHolder.DeleteRatioButtonStudentListener(student, context));
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
            gridLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
        expand.setOnClickListener(new StudentArrayListenerHolder.ExpandButtonStudentListener(student, context, expand, convertView));
    }

}

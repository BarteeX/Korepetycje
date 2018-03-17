package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RadioButton;

import com.example.monika.korepetycje.GUI.StudentCard.StudentCardActivity;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;

import java.util.List;
import java.util.Objects;

public class StudentArrayListenerHolder {

    public static class ButtonMessageListener
            extends DefaultListener
            implements View.OnClickListener {

        ButtonMessageListener(Student student, Activity context) {
            super(student, context);
        }

        @Override
        public void onClick(View view) {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.setData(Uri.parse("smsto:" + Uri.encode(super.student.getTelephoneNumber())));
            super.context.startActivity(smsIntent);
        }
    }

    public static class CallButtonListener
            extends DefaultListener
            implements View.OnClickListener {

        CallButtonListener(Student student, Activity context) {
            super(student, context);
        }

        @Override
        public void onClick(View view) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + student.getTelephoneNumber()));
            context.startActivity(callIntent);
        }
    }

    public static class EditButtonListener
            extends DefaultListener
            implements View.OnClickListener {

        EditButtonListener(Student student, Activity context) {
            super(student, context);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, StudentCardActivity.class);
            Intent studentIntent = intent.putExtra("studentId", student.getId());
            context.startActivity(studentIntent);
        }
    }

    public static class MapButtonListener
            extends DefaultListener
            implements View.OnClickListener {

        MapButtonListener(Student student, Activity context) {
            super(student, context);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View view) {final List<Address> addresses = student.getAddresses();
            if (addresses.size() > 1) {
                PopupMenu popupMenu = new PopupMenu(context, view.findViewById(R.id.map_button));
                Menu menu = popupMenu.getMenu();
                for (Address address1 : addresses) {
                    String label = address1.toString();
                    menu.add(label);
                }
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    String title = menuItem.getTitle().toString();
                    for (Address address : addresses) {
                        String label = address.toString();
                        if (Objects.equals(label, title)) {
                            return StudentArrayHelper.onMapItemSelection(address, context);
                        }
                    }
                    return false;
                });
                popupMenu.show();
            } else if (addresses.size() == 1) {
                Address address = addresses.get(0);
                StudentArrayHelper.onMapItemSelection(address, context);
            }
        }
    }

    public static class DeleteRatioButtonListener
            extends DefaultListener
            implements View.OnClickListener {

        DeleteRatioButtonListener(Student student, Activity context) {
            super(student, context);
        }

        @Override
        public void onClick(View view) {
            boolean toDelete = student.isToDelete();
            student.setToDelete(!toDelete);
            RadioButton radioButton = view.findViewById(R.id.delete_radio_button);
            radioButton.setChecked(!toDelete);
        }
    }
}

package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.monika.korepetycje.GUI.ApplicationHelper;
import com.example.monika.korepetycje.GUI.Controllers.ExpanderAnimation;
import com.example.monika.korepetycje.GUI.Controllers.StudentsList;
import com.example.monika.korepetycje.GUI.DialogWindows.LessonDurationPickerDialog;
import com.example.monika.korepetycje.GUI.DialogWindows.StartCounterDialog;
import com.example.monika.korepetycje.GUI.StudentCard.StudentCardActivity;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.StateMode;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;
import com.example.monika.korepetycje.managers.StudentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentArrayListenerHolder {

    public static class ButtonMessageStudentListener
            extends DefaultStudentListener
            implements View.OnClickListener {

        ButtonMessageStudentListener(Student student, Activity context) {
            super(student, context);
        }

        @Override
        public void onClick(View view) {
            ApplicationHelper.startMessageIntent(student.getTelephoneNumber(), context);
        }
    }

    public static class CallButtonStudentListener
            extends DefaultStudentListener
            implements View.OnClickListener {

        CallButtonStudentListener(Student student, Activity context) {
            super(student, context);
        }

        @Override
        public void onClick(View view) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + student.getTelephoneNumber()));
            context.startActivity(callIntent);
        }
    }

    public static class EditButtonStudentListener
            extends DefaultStudentListener
            implements View.OnClickListener {

        EditButtonStudentListener(Student student, Activity context) {
            super(student, context);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, StudentCardActivity.class);
            Intent studentIntent = intent.putExtra("studentId", student.getId());
            context.startActivity(studentIntent);
        }
    }

    public static class MapButtonStudentListener
            extends DefaultStudentListener
            implements View.OnClickListener {

        MapButtonStudentListener(Student student, Activity context) {
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

    public static class DeleteRatioButtonStudentListener
            extends DefaultStudentListener
            implements View.OnClickListener {

        DeleteRatioButtonStudentListener(Student student, Activity context) {
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

    public static class ExpandButtonStudentListener
            extends DefaultStudentListener
            implements View.OnClickListener {

        private TextView expand;
        private View convertView;

        ExpandButtonStudentListener(Student student, Activity context, TextView expand, View convertView) {
            super(student, context);
            this.expand = expand;
            this.convertView = convertView;
        }

        @Override
        public void onClick(View view) {
            GridLayout gridLayout = convertView.findViewById(R.id.expander);
            boolean isExpanded = student.isExpanded();
            int targetHeight = 100;
            int startHeight = 0;
            ExpanderAnimation resizeAnimation;
            if (isExpanded) {
                resizeAnimation = new ExpanderAnimation(gridLayout, startHeight, targetHeight);
                resizeAnimation.setDuration(200);
                gridLayout.startAnimation(resizeAnimation);


                expand.setText(R.string.expand);
            } else {
                resizeAnimation = new ExpanderAnimation(gridLayout, targetHeight, startHeight);
                resizeAnimation.setDuration(200);
                gridLayout.startAnimation(resizeAnimation);
                expand.setText(R.string.collapse);
            }
            student.setExpanded(!isExpanded);
        }
    }

    public static class FilterButtonListener
            implements View.OnClickListener {

        private EditText filterBox;
        private StudentsList studentsList;
        private StudentManager studentManager;
        private StudentsList context;

        public FilterButtonListener(StudentsList context) {
            this.studentsList = context;
            this.filterBox = context.findViewById(R.id.filter_box);
            this.studentManager = StudentManager.getInstance();
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            String filter = filterBox.getText().toString();
            List<Student> list = studentManager.filter(filter);
            studentsList.loadStudentsList(list);

            context.setFilter(filter);
            ApplicationHelper.hideWindowKeyboard(context);
        }
    }

    public static class ClearFilterButtonListener
            implements View.OnClickListener {

        private StudentsList studentsList;
        private StudentManager studentManager;
        private EditText filterBox;
        private StudentsList context;

        public ClearFilterButtonListener(StudentsList context, EditText filterBox) {
            this.studentsList = context;
            this.studentManager = StudentManager.getInstance();
            this.filterBox = filterBox;
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            List<Student> studentList = studentManager.getAll();
            studentsList.loadStudentsList(studentList);

            filterBox.setText("");
            ApplicationHelper.hideWindowKeyboard(context);
            context.clearFilter();
        }
    }

    public static class AcceptDeleteButtonListener
            implements View.OnClickListener {

        private List<Student> studentList;
        private List<Student> students;
        private StudentsArrayAdapter adapter;

        public AcceptDeleteButtonListener(List<Student> studentList, Activity activity) {
            this.studentList = studentList;
            StudentsList context = (StudentsList) activity;
            this.students = context.getStudents();
            this.adapter = context.getAdapter();
        }

        @Override
        public void onClick(View view) {
            List<Student> studentsToDelete = new ArrayList<>();
            for (int studentsSize = studentList.size(), i = studentsSize - 1; i >= 0; i--) {
                Student student = studentList.get(i);
                student.setStateMode(StateMode.Normal);
                if (student.isToDelete()) {
                    studentsToDelete.add(student);
                    student.delete();
                }
            }
            this.students.removeAll(studentsToDelete);
            adapter.notifyDataSetChanged();

            Button deleteButton = view.findViewById(R.id.accept_delete);
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public static class CounterButtonStudentListener
            extends DefaultStudentListener
            implements View.OnClickListener {

        private Dialog counterDialog;
        private Button counterButton;

        CounterButtonStudentListener(Student student, Activity context, Button counterButton) {
            super(student, context);
            this.counterButton = counterButton;
        }

        @TargetApi(Build.VERSION_CODES.O)
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View view) {
            List<Term> studentTerms = student.getTerms();

            counterDialog = new StartCounterDialog(context, student);

            if (studentTerms.size() > 1) {
                PopupMenu popupMenu = initPopupMenu(studentTerms);
                popupMenu.show();
            } else if (studentTerms.size() == 1) {
                counterDialog.show();
            } else if (studentTerms.size() == 0) {
                LessonDurationPickerDialog fragmentDialog = new LessonDurationPickerDialog();
                fragmentDialog.show(context.getFragmentManager(), "datePicker");
            }
        }

        private PopupMenu initPopupMenu(List<Term> studentTerms) {
            PopupMenu popupMenu = new PopupMenu(context, counterButton);
            Menu menu = popupMenu.getMenu();
            for (Term term : studentTerms) {
                String label = term.toString();
                menu.add(label);
            }
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                String title = menuItem.getTitle().toString();
                for (Term term : studentTerms) {
                    String label = term.toString();
                    if (label.equals(title)) {
                        return ApplicationHelper.startSingleTerm(term, context);
                    }
                }
                return false;
            });
            return popupMenu;
        }
    }
}

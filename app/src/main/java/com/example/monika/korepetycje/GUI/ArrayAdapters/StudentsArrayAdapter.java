package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.monika.korepetycje.database.models.Student;

import java.util.List;


public class StudentsArrayAdapter extends ArrayAdapter<Student> {
    private Activity context;
    private List<Student> students;

    @SuppressLint("UseSparseArrays")
    public StudentsArrayAdapter(Activity context, int resource, List<Student> students) {
        super(context, resource);

        this.context = context;
        this.students = students;
    }

    static class StudentViewHolder {
        public TextView name;
        public TextView surname;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Student student = students.get(position);

        convertView = StudentArrayHelper.getConvertView(student, context);

        StudentArrayHelper.setButtonsListeners(student, context, convertView, position);

        return convertView;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    public List<Student> getStudents() {
        return this.students;
    }

}

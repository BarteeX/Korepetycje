package com.example.monika.korepetycje.GUI;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Student;

import java.util.List;

/**
 * Created by Monika on 2018-01-28.
 */

public class StudentsArrayAdapter extends ArrayAdapter<Student> {
    private Activity context;
    private List<Student> students;

    public StudentsArrayAdapter(Activity context, int resource, List<Student> students) {
        super(context, resource);

        this.context = context;
        this.students = students;
    }

    static class StudentViewHolder {
        public ImageView avatar;
        public TextView name;
        public TextView surname;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        StudentViewHolder studentViewHolder;
        View rowView = convertView;

        Student student = students.get(position);

        if (rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.student_list_item, null, true);

            studentViewHolder = new StudentViewHolder();
            studentViewHolder.name = rowView.findViewById(R.id.name);
            studentViewHolder.surname = rowView.findViewById(R.id.surname);
            studentViewHolder.avatar = rowView.findViewById(R.id.avatar);

            rowView.setTag(studentViewHolder);

            rowView.setOnClickListener((View view) -> {
                Intent intent = new Intent(context, StudentCardEditable.class);
                Intent student1 = intent.putExtra("studentId", Integer.valueOf((int) student.getId()));
                context.startActivity(student1);
            });

        } else {
            studentViewHolder = (StudentViewHolder) rowView.getTag();
        }


        studentViewHolder.name.setText(student.getName());
        studentViewHolder.surname.setText(student.getSurname());

        return rowView;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    public List<Student> getStudents() {
        return this.students;
    }
}

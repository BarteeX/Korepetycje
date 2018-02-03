package com.example.monika.korepetycje.GUI;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Student;

import org.json.JSONObject;

import java.io.Serializable;
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

    static class ViewHolder {
        public ImageView avatar;
        public TextView name;
        public TextView surname;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;

        Student student = students.get(position);

        if (rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.student_list_item, null, true);

            viewHolder = new ViewHolder();
            viewHolder.name = rowView.findViewById(R.id.name);
            viewHolder.surname = rowView.findViewById(R.id.surname);
            viewHolder.avatar = rowView.findViewById(R.id.avatar);

            rowView.setTag(viewHolder);

            rowView.setOnClickListener((View view) -> {
                Intent intent = new Intent(context, StudentCardEditable.class);
                Intent student1 = intent.putExtra("studentId", student.getId());
                context.startActivity(student1);
            });
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }


        viewHolder.name.setText(student.getName());
        viewHolder.surname.setText(student.getSurname());

        return rowView;
    }

    @Override
    public int getCount() {
        return students.size();
    }
}

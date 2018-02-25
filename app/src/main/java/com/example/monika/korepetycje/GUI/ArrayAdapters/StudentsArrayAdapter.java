package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentsArrayAdapter extends ArrayAdapter<Student> {
    private Activity context;
    private List<Student> students;
    private Map<Integer, Integer> idsMap;

    @SuppressLint("UseSparseArrays")
    public StudentsArrayAdapter(Activity context, int resource, List<Student> students) {
        super(context, resource);

        this.context = context;
        this.students = students;
        this.idsMap = new HashMap<>();
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
        final long studentId = student.getId();

        if (rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.student_list_item, null, true);

            studentViewHolder = new StudentViewHolder();
            studentViewHolder.name = rowView.findViewById(R.id.name);
            studentViewHolder.surname = rowView.findViewById(R.id.surname);
            studentViewHolder.avatar = rowView.findViewById(R.id.avatar);

            rowView.setTag(studentViewHolder);

//            rowView.setOnClickListener((View view) -> {
//                Intent intent = new Intent(context, StudentCardEditable.class);
//                Intent student1 = intent.putExtra("studentId", Integer.valueOf((int) studentId));
//                context.startActivity(student1);
//            });

        } else {
            studentViewHolder = (StudentViewHolder) rowView.getTag();
        }

        if (rowView.getId() == -1)
            rowView.setId(0x7f08f000 + position);

        idsMap.put(position, rowView.getId());

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

    public Student getStudent(int position) {
        if (position < getCount())
            return getStudents().get(position);
        else
            return null;
    }

    public long getAdapterItemId(int position) {
        return idsMap.get(position);
    }
}

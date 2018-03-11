package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.monika.korepetycje.GUI.StudentCard.StudentCardActivity;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.StateMode;
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
        public TextView name;
        public TextView surname;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        StudentViewHolder studentViewHolder;

        final Student student = students.get(position);
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.student_list_item, null, true);

        studentViewHolder = new StudentViewHolder();
        studentViewHolder.name = convertView.findViewById(R.id.name);
        studentViewHolder.surname = convertView.findViewById(R.id.surname);

        convertView.setTag(studentViewHolder);

        Button messageMutton = convertView.findViewById(R.id.message_button);
        messageMutton.setOnClickListener(view -> {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.setData(Uri.parse("smsto:" + Uri.encode(student.getTelephoneNumber())));
            context.startActivity(smsIntent);
        });

        Button callButton = convertView.findViewById(R.id.call_button);
        callButton.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + student.getTelephoneNumber()));
            context.startActivity(callIntent);
        });

        Button editButton = convertView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, StudentCardActivity.class);
            Intent studentIntent = intent.putExtra("studentId", student.getId());
            context.startActivity(studentIntent);
        });

        TextView textView = convertView.findViewById(R.id.student_label);
        textView.setText(context.getString(R.string.student_label_text) + (position + 1));

        RadioButton radioButton = convertView.findViewById(R.id.delete_radio_button);
        radioButton.setVisibility(View.INVISIBLE);
        if (student.getStateMode() == StateMode.Delete) {
            radioButton.setVisibility(View.VISIBLE);
        }
        radioButton.setOnClickListener(v -> {
            boolean toDelete = student.isToDelete();
            student.setToDelete(!toDelete);
            radioButton.setChecked(!toDelete);
        });

        if (convertView.getId() == -1)
            convertView.setId(0x7f08f000 + position);

        idsMap.put(position, convertView.getId());

        studentViewHolder.name.setText(student.getName());
        studentViewHolder.surname.setText(student.getSurname());
        return convertView;
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

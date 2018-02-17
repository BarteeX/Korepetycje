package com.example.monika.korepetycje.GUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.monika.korepetycje.DataLoader;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.managers.StudentManager;

import java.util.List;

public class StudentsList extends AppCompatActivity {

    private ListView studentsListView;
    private StudentsArrayAdapter adapter;
    private List<Student> students;

    @Override
    protected void onCreate (Bundle instanceState) {
        super.onCreate(instanceState);

        DataLoader dataLoader = DataLoader.getInstance();
        dataLoader.loadData(this);

        setContentView(R.layout.students_list);

        studentsListView = findViewById(R.id.studentsList);

        students = StudentManager.getInstance().getAll();

        adapter = new StudentsArrayAdapter(this, R.layout.student_list_item, students);

        studentsListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.student_list_context_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_student:
                return addStudentSelection();
            case R.id.delete_student:
                return removeStudentSelection();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        students = StudentManager.getInstance().getAll();
        adapter.notifyDataSetChanged();

    }

    private boolean addStudentSelection() {

        Intent intent = new Intent(this, StudentCardEditable.class);
        Intent student = intent.putExtra("studentId", -1);
        startActivity(student);
        return true;
    }

    private boolean removeStudentSelection() {
        return true;
    }
}

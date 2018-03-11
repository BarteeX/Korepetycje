package com.example.monika.korepetycje.GUI.Controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.monika.korepetycje.DataLoader;
import com.example.monika.korepetycje.GUI.ArrayAdapters.StudentsArrayAdapter;
import com.example.monika.korepetycje.GUI.StudentCard.StudentCardActivity;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.StateMode;
import com.example.monika.korepetycje.database.DBHelper;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.managers.StudentManager;

import java.util.ArrayList;
import java.util.List;

public class StudentsList extends AppCompatActivity {

    private ListView studentsListView;
    public static StudentsArrayAdapter adapter;
    private List<Student> students;
    private static Context context;

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate (Bundle instanceState) {
        super.onCreate(instanceState);
        context = getApplicationContext();

        loadStudentsList();
    }

    private void loadStudentsList() {
        //context.deleteDatabase(DBHelper.DATABASE_NAME);

        DataLoader dataLoader = DataLoader.getInstance();
        dataLoader.loadDataToManagers();

        setContentView(R.layout.students_list);

        studentsListView = findViewById(R.id.studentsList);

        students = StudentManager.getInstance().getAll();

        adapter = new StudentsArrayAdapter(this, R.layout.student_list_item, students);

        studentsListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        studentsListView.setOnItemClickListener((adapterView, view, i, l) -> startStudentCardActivity(i));
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.student_list_context_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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

    private boolean addStudentSelection() {
        startStudentCardActivity(-1);
        return true;
    }


    private void startStudentCardActivity(int i) {
        Intent intent = new Intent(context, StudentCardActivity.class);
        Intent studentIntent = intent.putExtra("studentId", i);
        startActivity(studentIntent);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean removeStudentSelection() {
        StudentsArrayAdapter arrayAdapter = (StudentsArrayAdapter) studentsListView.getAdapter();
        List<Student> studentList = arrayAdapter.getStudents();

        for (int i = 0, length = studentList.size(); i < length; i++) {
             Student student = studentList.get(i);
             student.setStateMode(StateMode.Delete);
        }
        arrayAdapter.notifyDataSetChanged();

        Button deleteButton = findViewById(R.id.accept_delete);
        deleteButton.setVisibility(View.VISIBLE);

        deleteButton.setOnClickListener(view -> {
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
            deleteButton.setVisibility(View.INVISIBLE);
        });
        return true;
    }

    public static Context getContext() {
        return context;
    }
}

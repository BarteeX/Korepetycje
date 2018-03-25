package com.example.monika.korepetycje.GUI.Controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.monika.korepetycje.DataLoader;
import com.example.monika.korepetycje.GUI.ApplicationHelper;
import com.example.monika.korepetycje.GUI.ArrayAdapters.StudentArrayListenerHolder;
import com.example.monika.korepetycje.GUI.ArrayAdapters.StudentsArrayAdapter;
import com.example.monika.korepetycje.GUI.StudentCard.StudentCardActivity;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.StateMode;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.managers.StudentManager;

import java.util.List;

public class StudentsList extends AppCompatActivity {

    private ListView studentsListView;
    @SuppressLint("StaticFieldLeak")
    public static StudentsArrayAdapter adapter;
    private List<Student> students;
    @SuppressLint("StaticFieldLeak")
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

        loadStudentsList(null);
        setFilterBoxListeners();


        ApplicationHelper.hideWindowKeybord(this);
    }

    //WBW2018

    private void setFilterBoxListeners() {
        Button searchButton = findViewById(R.id.fire_filter_button);
        searchButton.setOnClickListener(new StudentArrayListenerHolder.FilterButtonListener(this));

        Button clearButton = findViewById(R.id.clear_filter_button);
        clearButton.setOnClickListener(new StudentArrayListenerHolder.ClearFilterButtonListener(this, findViewById(R.id.filter_box)));
    }

    public void loadStudentsList(@Nullable List<Student> studentList) {
        //context.deleteDatabase(DBHelper.DATABASE_NAME);

        if (studentList == null) {
            DataLoader dataLoader = DataLoader.getInstance();
            dataLoader.loadDataToManagers();

            setContentView(R.layout.students_list);

            studentsListView = findViewById(R.id.studentsList);
            studentsListView.setOnItemClickListener((adapterView, view, i, l) -> startStudentCardActivity(i));

            students = StudentManager.getInstance().getAll();

            adapter = new StudentsArrayAdapter(this, R.layout.students_list_item, students);

        } else {
            students.clear();
            students.addAll(studentList);
        }


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
        deleteButton.setOnClickListener(
                new StudentArrayListenerHolder.AcceptDeleteButtonListener(studentList, this)
        );
        return true;
    }

    public static Context getContext() {
        return context;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public StudentsArrayAdapter getAdapter() {
        return adapter;
    }
}

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

import java.util.ArrayList;
import java.util.List;

public class StudentsList extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    @SuppressLint("StaticFieldLeak")
    public static StudentsArrayAdapter adapter;

    private ListView studentsListView;
    private List<Student> students;

    private String filter;

    {
        this.students = new ArrayList<>();
        this.filter = "";
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
        ApplicationHelper.hideWindowKeyboard(this);
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
    public void onResume() {
        super.onResume();
        resumeList();
        ApplicationHelper.hideWindowKeyboard(this);
    }

    @Override
    protected void onCreate (Bundle instanceState) {
        super.onCreate(instanceState);
        context = getApplicationContext();

        loadData();
        prepareLayout();
        resumeList();

        setFilterBoxListeners();


        ApplicationHelper.hideWindowKeyboard(this);
    }

    private void loadData() {
        DataLoader dataLoader = DataLoader.getInstance();
        dataLoader.loadDataToManagers();
    }

    private void prepareLayout() {
        setContentView(R.layout.students_list);

        studentsListView = findViewById(R.id.studentsList);
        studentsListView.setOnItemClickListener((adapterView, view, i, l) -> startStudentCardActivity(i));

        students.addAll(StudentManager.getInstance().getAll());

        adapter = new StudentsArrayAdapter(this, R.layout.students_list_item, students);
        studentsListView.setAdapter(adapter);
    }

    public void resumeList() {
        String filter = getFilter().trim();
        StudentManager manager = StudentManager.getInstance();
        List<Student> list = new ArrayList<>();
        if(filter.trim().isEmpty()) {
            list.addAll(manager.getAll());
        } else {
            list.addAll(manager.filter(filter));
        }
        loadStudentsList(list);
    }

    public void loadStudentsList(List<Student> studentList) {
        students.clear();
        students.addAll(studentList);
        adapter.notifyDataSetChanged();
    }

    private void setFilterBoxListeners() {
        Button searchButton = findViewById(R.id.fire_filter_button);
        searchButton.setOnClickListener(new StudentArrayListenerHolder.FilterButtonListener(this));

        Button clearButton = findViewById(R.id.clear_filter_button);
        clearButton.setOnClickListener(new StudentArrayListenerHolder.ClearFilterButtonListener(this, findViewById(R.id.filter_box)));
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

    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public StudentsArrayAdapter getAdapter() {
        return adapter;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void clearFilter() {
        setFilter("");
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public static Context getContext() {
        return context;
    }
}

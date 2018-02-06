package com.example.monika.korepetycje.GUI;

import android.annotation.SuppressLint;
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

/**
 * Created by Monika on 2018-01-28.
 */

public class StudentsList extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle instanceState) {
        super.onCreate(instanceState);

        DataLoader dataLoader = DataLoader.getInstance();
        dataLoader.loadData(this);

        setContentView(R.layout.students_list);

        ListView studentsListView = findViewById(R.id.studentsList);

        Student student1 = new Student();
        student1.setName("Bartek");
        student1.setSurname("Wałcerz");

        student1.save(getApplicationContext());

        List<Student> students = StudentManager.getInstance().getAll();

        if (students.size() == 0) {
            Student student = new Student();
            student.setName("Brak");
            student.setSurname("Uczników");
            students.add(student);
        }

        StudentsArrayAdapter adapter = new StudentsArrayAdapter(this, R.layout.student_list_item, students);

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
            case R.id.new_game:
                System.out.println("NEW GAME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return true;
            case R.id.help:
                System.out.println("HEEEEEELP !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

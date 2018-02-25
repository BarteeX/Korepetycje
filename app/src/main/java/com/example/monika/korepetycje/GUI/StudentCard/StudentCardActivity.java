package com.example.monika.korepetycje.GUI.StudentCard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.example.monika.korepetycje.GUI.ArrayAdapters.AddressArrayAdapter;
import com.example.monika.korepetycje.GUI.ArrayAdapters.TermsArrayAdapter;
import com.example.monika.korepetycje.GUI.Controllers.StudentsList;
import com.example.monika.korepetycje.GUI.StudentCard.PageAdapter;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

import java.util.List;


public class StudentCardActivity extends AppCompatActivity {

    private Student student = null;
    private List<Term> terms = null;
    private List<Address> addresses = null;


    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        context = getApplicationContext();

        setStudentData();

        loadTabLayout();
        setSaveButtonListener();
    }

    private void setStudentData() {
        Intent intent = getIntent();
        Integer studentId =  intent.getIntExtra("studentId", -1);
        this.student = loadData(studentId);
        this.addresses = student.getAddresses();
        this.terms = student.getTerms();
    }

    private Student loadData(Integer studentId) {
        Student student;
        if (studentId >= 0 && studentId < StudentsList.adapter.getCount())
            student = StudentsList.adapter.getStudent(studentId);
        else
            student = new Student();
        return student;
    }

    private void loadTabLayout() {
        setContentView(R.layout.student_pager_view);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Dane"));
        tabLayout.addTab(tabLayout.newTab().setText("Adresy"));
        tabLayout.addTab(tabLayout.newTab().setText("Terminy"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);

        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), student);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setSaveButtonListener() {
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener((view) -> {

            ViewPager viewPager = findViewById(R.id.pager);
            if (viewPager != null) {
                EditText name = viewPager.findViewById(R.id.name);
                EditText surname = viewPager.findViewById(R.id.surname);
                EditText telephoneNumber = viewPager.findViewById(R.id.telephoneNumber);

                student.setName(name.getText().toString());
                student.setSurname(surname.getText().toString());
                student.setTelephoneNumber(telephoneNumber.getText().toString());
                student.setAddresses(addresses);
                student.setTerms(terms);
                student.save(context);
                finish();
            } else {
                System.out.println("PROBLEM Z ZAPISEM");
            }
        });
    }

    public static Context getContext() {
        return context;
    }
}

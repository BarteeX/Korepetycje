package com.example.monika.korepetycje.GUI.StudentCard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;
import com.example.monika.korepetycje.managers.StudentManager;

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
    }

    private void setStudentData() {
        Intent intent = getIntent();
        Long studentId = intent.getLongExtra("studentId", -1);
        this.student = loadData(studentId);
        this.addresses = student.getAddresses();
        this.terms = student.getTerms();
    }

    private Student loadData(Long studentId) {
        StudentManager studentManager = StudentManager.getInstance();
        return studentManager.getById(studentId);
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


    public static Context getContext() {
        return context;
    }
}

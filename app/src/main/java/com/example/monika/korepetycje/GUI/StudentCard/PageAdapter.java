package com.example.monika.korepetycje.GUI.StudentCard;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.monika.korepetycje.database.models.Student;


public class PageAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;
    private Student student = null;


    public PageAdapter(FragmentManager fm, int NumOfTabs,@NonNull Student student) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.student = student;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MainCard(student);
            case 1:
                return new AddressesCard(student);
            case 2:
                return new TermsCard(student);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

package com.example.monika.korepetycje.database.CRUDAdapters;


import com.example.monika.korepetycje.database.LessonsSQLiteOpenHelper;

/**
 * Created by Monika on 2018-02-17.
 */

public abstract class Adapter {
    protected LessonsSQLiteOpenHelper databaseHelper;

    protected Adapter () {
        setAdapter();
    }

    private void setAdapter () {
        databaseHelper = LessonsSQLiteOpenHelper.getInstance();
    }
}

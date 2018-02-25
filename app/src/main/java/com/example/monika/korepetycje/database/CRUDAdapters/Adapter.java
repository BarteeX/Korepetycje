package com.example.monika.korepetycje.database.CRUDAdapters;


import com.example.monika.korepetycje.database.LessonsSQLiteOpenHelper;


public abstract class Adapter {
    protected LessonsSQLiteOpenHelper databaseHelper;

    protected Adapter () {
        setAdapter();
    }

    private void setAdapter () {
        databaseHelper = LessonsSQLiteOpenHelper.getInstance();
    }
}

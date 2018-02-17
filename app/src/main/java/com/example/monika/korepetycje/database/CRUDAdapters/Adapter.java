package com.example.monika.korepetycje.database.CRUDAdapters;

import android.content.Context;

import com.example.monika.korepetycje.database.LessonsSQLiteOpenHelper;

/**
 * Created by Monika on 2018-02-17.
 */

public abstract class Adapter {
    protected LessonsSQLiteOpenHelper databaseHelper;

    protected Adapter (Context context) {
        setAdapter(context);
    }

    private void setAdapter (Context context) {
        databaseHelper = new LessonsSQLiteOpenHelper(context);
    }
}

package com.example.monika.korepetycje.database.CRUDAdapters;


import android.content.Context;

import com.example.monika.korepetycje.GUI.Controllers.StudentsList;
import com.example.monika.korepetycje.GUI.StudentCard.StudentCardActivity;
import com.example.monika.korepetycje.database.LessonsSQLiteOpenHelper;


abstract class Adapter {
    LessonsSQLiteOpenHelper databaseHelper;

    Adapter() {
        setAdapter();
    }

    private void setAdapter () {
        Context context = StudentCardActivity.getContext();
        if (context == null)
            context = StudentsList.getContext();
        databaseHelper = new LessonsSQLiteOpenHelper(context);
    }
}

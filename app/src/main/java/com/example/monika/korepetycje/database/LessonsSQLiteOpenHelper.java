package com.example.monika.korepetycje.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class LessonsSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int FIRST_VERSION = 1;


    public LessonsSQLiteOpenHelper(Context context) {
        super(context, DBHelper.INSTANCE.getDATABASE_NAME(), null,  FIRST_VERSION);
        SQLiteDatabase database = context.openOrCreateDatabase(DBHelper.INSTANCE.getDATABASE_NAME(), Context.MODE_PRIVATE, null);
        onCreate(database);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("DB - onCreate ....");
        try {
            String studentQuery = DBHelper.INSTANCE.CREATE_STUDENT_TABLE();
            String addressQuery = DBHelper.INSTANCE.CREATE_ADDRESS_TABLE();
            String termQuery = DBHelper.INSTANCE.CREATE_TERM_TABLE();

            sqLiteDatabase.execSQL(studentQuery);
            sqLiteDatabase.execSQL(addressQuery);
            sqLiteDatabase.execSQL(termQuery);
        } catch (SQLiteException e) {
            System.out.println("DB - creating failure...");
            return;
        }
        System.out.println("DB - create successful...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("DB = onUpgrade...");

        try {
            sqLiteDatabase.execSQL(DBHelper.INSTANCE.DROP_TABLE(DBHelper.INSTANCE.getTERM_TABLE_NAME()));
            sqLiteDatabase.execSQL(DBHelper.INSTANCE.DROP_TABLE(DBHelper.INSTANCE.getADDRESS_TABLE_NAME()));
            sqLiteDatabase.execSQL(DBHelper.INSTANCE.DROP_TABLE(DBHelper.INSTANCE.getSTUDENT_TABLE_NAME()));
        } catch (SQLiteException e) {
            System.out.println("DB - upgrade failure...");
        }

    }

}

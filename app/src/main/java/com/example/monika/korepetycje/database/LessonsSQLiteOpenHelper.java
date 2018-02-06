package com.example.monika.korepetycje.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Monika on 2018-01-23.
 */

public class LessonsSQLiteOpenHelper extends SQLiteOpenHelper{
    private static final int FIRST_VERSION = 1;
    private Context context;

    private static int counter = 0;

    public LessonsSQLiteOpenHelper(Context context) {
        super(context, DBHelper.DATABASE_NAME, null,  FIRST_VERSION);
        SQLiteDatabase database = context.openOrCreateDatabase(DBHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
        onCreate(database);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("DB - onCreate ....");
        if(counter == 0) {
            onUpgrade(sqLiteDatabase, 0, 0);
            counter++;
        }

        try {
            String studentQuery = DBHelper.CREATE_STUDENT_TABLE();
            String addressQuery = DBHelper.CREATE_ADDRESS_TABLE();
            String termQuery = DBHelper.CREATE_TERM_TABLE();

            sqLiteDatabase.execSQL(studentQuery);
            sqLiteDatabase.execSQL(addressQuery);
            sqLiteDatabase.execSQL(termQuery);
        } catch (SQLiteException e) {
            System.out.println("DB - creating failure...");
            //DbMessager.message(thisContext, e.getMessage());
            return;
        }
        System.out.println("DB - create successful...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("DB = onUpgrade...");

        try {
            sqLiteDatabase.execSQL(DBHelper.DROP_TABLE(DBHelper.TERM_TABLE_NAME));
            sqLiteDatabase.execSQL(DBHelper.DROP_TABLE(DBHelper.ADDRESS_TABLE_NAME));
            sqLiteDatabase.execSQL(DBHelper.DROP_TABLE(DBHelper.STUDENT_TABLE_NAME));
            if (counter != 0) {
                onCreate(sqLiteDatabase);
            }
        } catch (SQLiteException e) {
            System.out.println("DB - upgrade failure...");
            //DbMessager.message(thisContext, e.getMessage());
        }

    }

}

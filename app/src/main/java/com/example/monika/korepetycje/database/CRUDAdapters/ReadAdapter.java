package com.example.monika.korepetycje.database.CRUDAdapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.DBHelper;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;
import com.example.monika.korepetycje.managers.AddressManager;
import com.example.monika.korepetycje.managers.Manager;
import com.example.monika.korepetycje.managers.StudentManager;
import com.example.monika.korepetycje.managers.TermManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 2018-02-17.
 */

public class ReadAdapter extends Adapter {

    private static ReadAdapter instance = null;

    private ReadAdapter() {
        super();
    }

    public static ReadAdapter getInstance() {
        if(instance == null) {
            instance = new ReadAdapter();
        }
        return instance;
    }

    public <T extends DatabaseModel> List<? extends DatabaseModel> get(Manager<T> manager) {
        if (manager instanceof StudentManager) {
            return getStudents();
        } else if (manager instanceof AddressManager) {
            return getAddresses();
        } else if (manager instanceof TermManager) {
            return getTerms();
        } else {
            return null;
        }
    }

    private List<Student> getStudents() {
        List<Student> list = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String[] columnsName = AdapterUtils.getStudentColumnNames();

        try (Cursor cursor = database.query(DBHelper.STUDENT_TABLE_NAME, columnsName, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                Student student = AdapterUtils.getStudentFromCursor(cursor);
                list.add(student);
            }
        }

        return list;
    }

    private List<Address> getAddresses() {
        List<Address> list = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String[] columnNames = AdapterUtils.getAddressColumnNames();

        try (Cursor cursor = database.query(DBHelper.ADDRESS_TABLE_NAME, columnNames, null, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                Address address = AdapterUtils.getAddressFromCursor(cursor);
                list.add(address);
            }
        }

        return list;
    }

    private List<Term> getTerms() {
        List<Term> list = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String[] columns = AdapterUtils.getTermColumnNames();

        try (Cursor cursor = database.query(DBHelper.TERM_TABLE_NAME, columns, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                Term term = AdapterUtils.getTermFromCursor(cursor);
                list.add(term);
            }
        }

        return list;
    }
}

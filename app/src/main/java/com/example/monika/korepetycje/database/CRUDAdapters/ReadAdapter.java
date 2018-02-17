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

    private ReadAdapter(Context context) {
        super(context);
    }

    public static ReadAdapter getInstance(Context context) {
        if(instance == null) {
            instance = new ReadAdapter(context);
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

        String[] columnsName = {
                DBHelper.STUDENT_ID_PK,
                DBHelper.STUDENT_NAME,
                DBHelper.STUDENT_SURNAME,
                DBHelper.STUDENT_TELEPHONE_NR
        };

        try (Cursor cursor = database.query(DBHelper.STUDENT_TABLE_NAME, columnsName, null, null, null, null, null)) {

            while (cursor.moveToNext()) {
                long id = cursor.getInt(cursor.getColumnIndex(DBHelper.STUDENT_ID_PK));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_NAME));
                String surname = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_SURNAME));
                String telephoneNr = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_TELEPHONE_NR));

                Student student = new Student();
                student.setId(id);
                student.setName(name);
                student.setSurname(surname);
                student.setTelephoneNumber(telephoneNr);

                student.isLoaded();

                list.add(student);
            }
        }

        return list;
    }

    private List<Address> getAddresses() {
        List<Address> list = new ArrayList<>();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String[] columnNames = {
                DBHelper.ADDRESS_ID_PK,
                DBHelper.ADDRESS_CITY,
                DBHelper.ADDRESS_FLAT,
                DBHelper.ADDRESS_HOUSE_NR,
                DBHelper.ADDRESS_STREET,
                DBHelper.STUDENT_ID_FK
        };

        try (Cursor cursor = database.query(DBHelper.ADDRESS_TABLE_NAME, columnNames, null, null, null, null, null, null)) {

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DBHelper.ADDRESS_ID_PK));
                String city = cursor.getString(cursor.getColumnIndex(DBHelper.ADDRESS_CITY));
                String flat = cursor.getString(cursor.getColumnIndex(DBHelper.ADDRESS_FLAT));
                String houseNr = cursor.getString(cursor.getColumnIndex(DBHelper.ADDRESS_HOUSE_NR));
                String street = cursor.getString(cursor.getColumnIndex(DBHelper.ADDRESS_STREET));
                int studentId = cursor.getInt(cursor.getColumnIndex(DBHelper.STUDENT_ID_FK));

                Address address = new Address(studentId);
                address.setId(id);
                address.setCity(city);
                address.setFlatNumber(flat);
                address.setHouseNumber(houseNr);
                address.setStreet(street);

                address.isLoaded();

                list.add(address);
            }
        }

        return list;
    }

    private List<Term> getTerms() {
        List<Term> list = new ArrayList<>();

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String[] columns = {
                DBHelper.TERM_ID_PK,
                DBHelper.TERM_DAY,
                DBHelper.TERM_LENGTH,
                DBHelper.TERM_HOUR,
                DBHelper.ADDRESS_ID_FK,
                DBHelper.STUDENT_ID_FK
        };

        try (Cursor cursor = database.query(DBHelper.TERM_TABLE_NAME, columns, null, null, null, null, null)) {

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DBHelper.TERM_ID_PK));
                String day = cursor.getString(cursor.getColumnIndex(DBHelper.TERM_DAY));
                String length = cursor.getString(cursor.getColumnIndex(DBHelper.TERM_LENGTH));
                String hour = cursor.getString(cursor.getColumnIndex(DBHelper.TERM_HOUR));
                int addressId = cursor.getInt(cursor.getColumnIndex(DBHelper.ADDRESS_ID_FK));
                int studentId = cursor.getInt(cursor.getColumnIndex(DBHelper.STUDENT_ID_FK));

                Term term = new Term(studentId, addressId);
                term.setId(id);
                term.setDay(day);
                term.setTime(hour);
                term.setLength(length);

                term.isLoaded();

                list.add(term);
            }
        }

        return list;
    }
}

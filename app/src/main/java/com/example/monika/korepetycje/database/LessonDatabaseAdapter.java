package com.example.monika.korepetycje.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;
import com.example.monika.korepetycje.managers.AddressManager;
import com.example.monika.korepetycje.managers.Manager;
import com.example.monika.korepetycje.managers.StudentManager;
import com.example.monika.korepetycje.managers.TermManager;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 2018-01-27.
 */

public class LessonDatabaseAdapter {

    private LessonsSQLiteOpenHelper databaseHelper = null;
    private static LessonDatabaseAdapter instance = null;

    private LessonDatabaseAdapter(Context context) {
        databaseHelper = new LessonsSQLiteOpenHelper(context);
    }

    public static LessonDatabaseAdapter getInstance(Context context) {
        if(instance == null) {
            instance = new LessonDatabaseAdapter(context);
        }
        return instance;
    }

    public long save(DatabaseModel model) {
        if (model instanceof Address) {
            return save((Address) model);
        } else if (model instanceof Student) {
            return save((Student) model);
        } else if (model instanceof Term) {
            return save((Term) model);
        }
        return -1;
    }

    public long save(Student student) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.STUDENT_NAME, student.getName());
        contentValues.put(DBHelper.STUDENT_SURNAME, student.getSurname());
        contentValues.put(DBHelper.STUDENT_TELEPHONE_NR, student.getTelephoneNumber());

        return database.insert(DBHelper.STUDENT_TABLE_NAME , null, contentValues);

    }

    public long save(Address address) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.ADDRESS_CITY, address.getCity());
        contentValues.put(DBHelper.ADDRESS_FLAT, address.getFlatNumber());
        contentValues.put(DBHelper.ADDRESS_HOUSE_NR, address.getHouseNumber());
        contentValues.put(DBHelper.ADDRESS_STREET, address.getStreet());
        contentValues.put(DBHelper.STUDENT_ID_FK, address.getStudentId());

        return database.insert(DBHelper.ADDRESS_TABLE_NAME , null, contentValues);
    }

    public long save(Term term) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TERM_DAY, term.getDay());
        contentValues.put(DBHelper.TERM_LENGTH, term.getLength());
        contentValues.put(DBHelper.TERM_HOUR, term.getTime());
        contentValues.put(DBHelper.ADDRESS_ID_FK, term.getAddressId());
        contentValues.put(DBHelper.STUDENT_ID_FK, term.getStudentId());

        return database.insert(DBHelper.TERM_TABLE_NAME , null, contentValues);
    }

    public List<Student> getStudents() {
        List<Student> list = new ArrayList<>();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String[] columnsName = {
                DBHelper.STUDENT_NAME,
                DBHelper.STUDENT_SURNAME,
                DBHelper.STUDENT_TELEPHONE_NR
        };

        try (Cursor cursor = database.query(DBHelper.STUDENT_TABLE_NAME, columnsName, null, null, null, null, null)) {

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_NAME));
                String surname = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_SURNAME));
                String telephoneNr = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_TELEPHONE_NR));

                Student student = new Student();
                student.setName(name);
                student.setSurname(surname);
                student.setTelephoneNumber(telephoneNr);

                list.add(student);
            }
        }

        return list;
    }

    public List<Address> getAddresses() {
        List<Address> list = new ArrayList<>();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String[] columnNames = {
                DBHelper.ADDRESS_CITY,
                DBHelper.ADDRESS_FLAT,
                DBHelper.ADDRESS_HOUSE_NR,
                DBHelper.ADDRESS_STREET,
                DBHelper.STUDENT_ID_FK
        };

        try (Cursor cursor = database.query(DBHelper.ADDRESS_TABLE_NAME, columnNames, null, null, null, null, null, null)) {

            while (cursor.moveToNext()) {
                String city = cursor.getString(cursor.getColumnIndex(DBHelper.ADDRESS_CITY));
                String flat = cursor.getString(cursor.getColumnIndex(DBHelper.ADDRESS_FLAT));
                String houseNr = cursor.getString(cursor.getColumnIndex(DBHelper.ADDRESS_HOUSE_NR));
                String street = cursor.getString(cursor.getColumnIndex(DBHelper.ADDRESS_STREET));
                int studentId = cursor.getInt(cursor.getColumnIndex(DBHelper.STUDENT_ID_FK));

                Address address = new Address(studentId);
                address.setCity(city);
                address.setFlatNumber(flat);
                address.setHouseNumber(houseNr);
                address.setStreet(street);

                list.add(address);
            }
        }

        return list;
    }

    public List<Term> getTerms() {
        List<Term> list = new ArrayList<>();

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String[] columns = {
                DBHelper.TERM_DAY,
                DBHelper.TERM_LENGTH,
                DBHelper.TERM_HOUR,
                DBHelper.ADDRESS_ID_FK,
                DBHelper.STUDENT_ID_FK
        };

        try (Cursor cursor = database.query(DBHelper.TERM_TABLE_NAME, columns, null, null, null, null, null)) {

            while (cursor.moveToNext()) {
                String day = cursor.getString(cursor.getColumnIndex(DBHelper.TERM_DAY));
                String length = cursor.getString(cursor.getColumnIndex(DBHelper.TERM_LENGTH));
                String hour = cursor.getString(cursor.getColumnIndex(DBHelper.TERM_HOUR));
                int addressId = cursor.getInt(cursor.getColumnIndex(DBHelper.ADDRESS_ID_FK));
                int studentId = cursor.getInt(cursor.getColumnIndex(DBHelper.STUDENT_ID_FK));

                Term term = new Term(studentId, addressId);
                term.setDay(day);
                term.setTime(hour);
                term.setLength(length);

                list.add(term);
            }
        }

        return list;
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

    /*
    *
    * public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
        return count;
    }*/

}

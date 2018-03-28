package com.example.monika.korepetycje.database.CRUDAdapters;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.monika.korepetycje.database.DBHelper;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

/**
 * Created by Monika on 2018-02-17.
 */

public class AdapterUtils {
    public static String[] getStudentColumnNames() {
        return new String[]{
                DBHelper.STUDENT_ID_PK,
                DBHelper.STUDENT_NAME,
                DBHelper.STUDENT_SURNAME,
                DBHelper.STUDENT_TELEPHONE_NR
        };
    }

    public static String[] getAddressColumnNames() {
        return new String[] {
                DBHelper.ADDRESS_ID_PK,
                DBHelper.ADDRESS_CITY,
                DBHelper.ADDRESS_FLAT,
                DBHelper.ADDRESS_HOUSE_NR,
                DBHelper.ADDRESS_STREET,
                DBHelper.STUDENT_ID_FK
        };
    }

    public static String[] getTermColumnNames() {
        return new String[] {
                DBHelper.TERM_ID_PK,
                DBHelper.TERM_DAY,
                DBHelper.TERM_LENGTH,
                DBHelper.TERM_HOUR,
                DBHelper.ADDRESS_ID_FK,
                DBHelper.STUDENT_ID_FK
        };
    }

    public static Student getStudentFromCursor(Cursor cursor) {
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

        return student;
    }

    public static Address getAddressFromCursor(Cursor cursor) {
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

        return address;
    }

    public static Term getTermFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DBHelper.TERM_ID_PK));
        String day = cursor.getString(cursor.getColumnIndex(DBHelper.TERM_DAY));
        String length = cursor.getString(cursor.getColumnIndex(DBHelper.TERM_LENGTH));
        String hour = cursor.getString(cursor.getColumnIndex(DBHelper.TERM_HOUR));
        int addressId = cursor.getInt(cursor.getColumnIndex(DBHelper.ADDRESS_ID_FK));
        int studentId = cursor.getInt(cursor.getColumnIndex(DBHelper.STUDENT_ID_FK));

        Term term = new Term(studentId, addressId);
        term.setId(id);
        term.setDay(day);
        term.setTimeFrom(hour);
        term.setTimeTo(length);

        term.isLoaded();

        return term;
    }

    public static ContentValues getContentValues(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.STUDENT_NAME, student.getName());
        contentValues.put(DBHelper.STUDENT_SURNAME, student.getSurname());
        contentValues.put(DBHelper.STUDENT_TELEPHONE_NR, student.getTelephoneNumber());
        return contentValues;
    }

    public static ContentValues getContentValues(Address address) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.ADDRESS_CITY, address.getCity());
        contentValues.put(DBHelper.ADDRESS_FLAT, address.getFlatNumber());
        contentValues.put(DBHelper.ADDRESS_HOUSE_NR, address.getHouseNumber());
        contentValues.put(DBHelper.ADDRESS_STREET, address.getStreet());
        contentValues.put(DBHelper.STUDENT_ID_FK, address.getStudentId());
        return contentValues;
    }

    public static ContentValues getContentValues(Term term) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TERM_DAY, term.getDay());
        contentValues.put(DBHelper.TERM_LENGTH, term.getTimeTo());
        contentValues.put(DBHelper.TERM_HOUR, term.getTimeFrom());
        contentValues.put(DBHelper.ADDRESS_ID_FK, term.getAddressId());
        contentValues.put(DBHelper.STUDENT_ID_FK, term.getStudentId());
        return contentValues;
    }
}

package com.example.monika.korepetycje.database.CRUDAdapters;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.monika.korepetycje.database.DBHelper;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

class AdapterUtils {
    static String[] getStudentColumnNames() {
        return new String[]{
                DBHelper.INSTANCE.getSTUDENT_ID_PK(),
                DBHelper.INSTANCE.getSTUDENT_NAME(),
                DBHelper.INSTANCE.getSTUDENT_SURNAME(),
                DBHelper.INSTANCE.getSTUDENT_TELEPHONE_NR()
        };
    }

    static String[] getAddressColumnNames() {
        return new String[] {
                DBHelper.INSTANCE.getADDRESS_ID_PK(),
                DBHelper.INSTANCE.getADDRESS_CITY(),
                DBHelper.INSTANCE.getADDRESS_FLAT(),
                DBHelper.INSTANCE.getADDRESS_HOUSE_NR(),
                DBHelper.INSTANCE.getADDRESS_STREET(),
                DBHelper.INSTANCE.getSTUDENT_ID_FK()
        };
    }

    static String[] getTermColumnNames() {
        return new String[] {
                DBHelper.INSTANCE.getTERM_ID_PK(),
                DBHelper.INSTANCE.getTERM_DAY(),
                DBHelper.INSTANCE.getTERM_LENGTH(),
                DBHelper.INSTANCE.getTERM_HOUR(),
                DBHelper.INSTANCE.getADDRESS_ID_FK(),
                DBHelper.INSTANCE.getSTUDENT_ID_FK()
        };
    }

    static Student getStudentFromCursor(Cursor cursor) {
        long id = cursor.getInt(cursor.getColumnIndex(DBHelper.INSTANCE.getSTUDENT_ID_PK()));
        String name = cursor.getString(cursor.getColumnIndex(DBHelper.INSTANCE.getSTUDENT_NAME()));
        String surname = cursor.getString(cursor.getColumnIndex(DBHelper.INSTANCE.getSTUDENT_SURNAME()));
        String telephoneNr = cursor.getString(cursor.getColumnIndex(DBHelper.INSTANCE.getSTUDENT_TELEPHONE_NR()));

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setSurname(surname);
        student.setTelephoneNumber(telephoneNr);

        student.isLoaded();

        return student;
    }

    static Address getAddressFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DBHelper.INSTANCE.getADDRESS_ID_PK()));
        String city = cursor.getString(cursor.getColumnIndex(DBHelper.INSTANCE.getADDRESS_CITY()));
        String flat = cursor.getString(cursor.getColumnIndex(DBHelper.INSTANCE.getADDRESS_FLAT()));
        String houseNr = cursor.getString(cursor.getColumnIndex(DBHelper.INSTANCE.getADDRESS_HOUSE_NR()));
        String street = cursor.getString(cursor.getColumnIndex(DBHelper.INSTANCE.getADDRESS_STREET()));
        int studentId = cursor.getInt(cursor.getColumnIndex(DBHelper.INSTANCE.getSTUDENT_ID_FK()));

        Address address = new Address(studentId);
        address.setId(id);
        address.setCity(city);
        address.setFlatNumber(flat);
        address.setHouseNumber(houseNr);
        address.setStreet(street);

        address.isLoaded();

        return address;
    }

    static Term getTermFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DBHelper.INSTANCE.getTERM_ID_PK()));
        String day = cursor.getString(cursor.getColumnIndex(DBHelper.INSTANCE.getTERM_DAY()));
        String length = cursor.getString(cursor.getColumnIndex(DBHelper.INSTANCE.getTERM_LENGTH()));
        String hour = cursor.getString(cursor.getColumnIndex(DBHelper.INSTANCE.getTERM_HOUR()));
        int addressId = cursor.getInt(cursor.getColumnIndex(DBHelper.INSTANCE.getADDRESS_ID_FK()));
        int studentId = cursor.getInt(cursor.getColumnIndex(DBHelper.INSTANCE.getSTUDENT_ID_FK()));

        Term term = new Term(studentId, addressId);
        term.setId(id);
        term.setDay(day);
        term.setTimeFrom(hour);
        term.setTimeTo(length);

        term.isLoaded();

        return term;
    }

    static ContentValues getContentValues(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.INSTANCE.getSTUDENT_NAME(), student.getName());
        contentValues.put(DBHelper.INSTANCE.getSTUDENT_SURNAME(), student.getSurname());
        contentValues.put(DBHelper.INSTANCE.getSTUDENT_TELEPHONE_NR(), student.getTelephoneNumber());
        return contentValues;
    }

    static ContentValues getContentValues(Address address) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.INSTANCE.getADDRESS_CITY(), address.getCity());
        contentValues.put(DBHelper.INSTANCE.getADDRESS_FLAT(), address.getFlatNumber());
        contentValues.put(DBHelper.INSTANCE.getADDRESS_HOUSE_NR(), address.getHouseNumber());
        contentValues.put(DBHelper.INSTANCE.getADDRESS_STREET(), address.getStreet());
        contentValues.put(DBHelper.INSTANCE.getSTUDENT_ID_FK(), address.getStudentId());
        return contentValues;
    }

    static ContentValues getContentValues(Term term) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.INSTANCE.getTERM_DAY(), term.getDay());
        contentValues.put(DBHelper.INSTANCE.getTERM_LENGTH(), term.getTimeTo());
        contentValues.put(DBHelper.INSTANCE.getTERM_HOUR(), term.getTimeFrom());
        contentValues.put(DBHelper.INSTANCE.getADDRESS_ID_FK(), term.getAddressId());
        contentValues.put(DBHelper.INSTANCE.getSTUDENT_ID_FK(), term.getStudentId());
        return contentValues;
    }
}

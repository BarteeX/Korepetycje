package com.example.monika.korepetycje.database.CRUDAdapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.DBHelper;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

import java.util.List;

/**
 * Created by Monika on 2018-02-17.
 */

public class UpdateAdapter extends Adapter {

    private static UpdateAdapter instance = null;

    private UpdateAdapter(Context context) {
        super(context);
    }

    public static UpdateAdapter getInstance(Context context) {
        if(instance == null) {
            instance = new UpdateAdapter(context);
        }
        return instance;
    }

    public long update(DatabaseModel model) {
        if (model instanceof Address) {
            return update(model);
        } else if (model instanceof Student) {
            return update(model);
        } else if (model instanceof Term) {
            return update(model);
        }
        return 0;
    }

    private long update(Student student) {
        System.out.println("UPDATING STUDENT = " + student.getName() + " " + student.getSurname());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.STUDENT_NAME, student.getName());
        contentValues.put(DBHelper.STUDENT_SURNAME, student.getSurname());
        contentValues.put(DBHelper.STUDENT_TELEPHONE_NR, student.getTelephoneNumber());

        List<Address> addresses = student.getAddresses();
        for (int i = 0, addressesSize = addresses.size(); i < addressesSize; i++) {
            Address address = addresses.get(i);
            long addressesCount = update(address);
        }

        List<Term> terms = student.getTerms();
        for (int i = 0, termsSize = terms.size(); i < termsSize; i++) {
            Term term = terms.get(i);
            long termsCount = update(term);
        }

        String[] whereArgs = {
                String.valueOf(student.getId())
        };

        return database.update (
                DBHelper.STUDENT_TABLE_NAME,
                contentValues,
                DBHelper.STUDENT_ID_PK + " = ?",
                whereArgs
        );
    }

    private long update(Address address) {
        System.out.println("UPDATING ADDRESS = " + address.toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.ADDRESS_CITY, address.getCity());
        contentValues.put(DBHelper.ADDRESS_FLAT, address.getFlatNumber());
        contentValues.put(DBHelper.ADDRESS_HOUSE_NR, address.getHouseNumber());
        contentValues.put(DBHelper.ADDRESS_STREET, address.getStreet());
        contentValues.put(DBHelper.STUDENT_ID_FK, address.getStudentId());

        String[] whereArgs = {
                String.valueOf(address.getId())
        };

        return database.update (
                DBHelper.ADDRESS_TABLE_NAME,
                contentValues,
                DBHelper.ADDRESS_ID_PK + " = ?",
                whereArgs
        );
    }

    private long update(Term term) {
        System.out.println("UPDATING TERM = " + term.toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TERM_DAY, term.getDay());
        contentValues.put(DBHelper.TERM_LENGTH, term.getLength());
        contentValues.put(DBHelper.TERM_HOUR, term.getTime());
        contentValues.put(DBHelper.ADDRESS_ID_FK, term.getAddressId());
        contentValues.put(DBHelper.STUDENT_ID_FK, term.getStudentId());

        String[] whereArgs = {
                String.valueOf(term.getId())
        };

        return database.update (
                DBHelper.TERM_TABLE_NAME,
                contentValues,
                DBHelper.TERM_ID_PK + " = ?",
                whereArgs
        );
    }
}

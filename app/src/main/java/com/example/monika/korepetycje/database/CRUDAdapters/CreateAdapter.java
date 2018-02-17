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

public class CreateAdapter extends Adapter {

    private static CreateAdapter instance = null;

    private CreateAdapter(Context context) {
        super(context);
    }

    public static CreateAdapter getInstance(Context context) {
        if(instance == null) {
            instance = new CreateAdapter(context);
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

    private long save(Student student) {
        System.out.println("SAVING STUDENT = " + student.getName() + " " + student.getSurname());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.STUDENT_NAME, student.getName());
        contentValues.put(DBHelper.STUDENT_SURNAME, student.getSurname());
        contentValues.put(DBHelper.STUDENT_TELEPHONE_NR, student.getTelephoneNumber());

        List<Address> addresses = student.getAddresses();
        for (int i = 0, addressesSize = addresses.size(); i < addressesSize; i++) {
            Address address = addresses.get(i);
            long id = save(address);

            if (address.isNew()) {
                address.setId(id);
            }
        }

        List<Term> terms = student.getTerms();
        for (int i = 0, termsSize = terms.size(); i < termsSize; i++) {
            Term term = terms.get(i);
            long id = save(term);

            if (term.isNew()) {
                term.setId(id);
            }
        }


        long id = database.insert(DBHelper.STUDENT_TABLE_NAME , null, contentValues);
        if (student.isNew()) {
            student.setId(id);
        }

        return id;
    }

    private long save(Address address) {
        System.out.println("SAVING ADDRESS = " + address.toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.ADDRESS_CITY, address.getCity());
        contentValues.put(DBHelper.ADDRESS_FLAT, address.getFlatNumber());
        contentValues.put(DBHelper.ADDRESS_HOUSE_NR, address.getHouseNumber());
        contentValues.put(DBHelper.ADDRESS_STREET, address.getStreet());
        contentValues.put(DBHelper.STUDENT_ID_FK, address.getStudentId());

        return database.insert(DBHelper.ADDRESS_TABLE_NAME , null, contentValues);
    }

    private long save(Term term) {
        System.out.println("SAVING TERM = " + term.toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TERM_DAY, term.getDay());
        contentValues.put(DBHelper.TERM_LENGTH, term.getLength());
        contentValues.put(DBHelper.TERM_HOUR, term.getTime());
        contentValues.put(DBHelper.ADDRESS_ID_FK, term.getAddressId());
        contentValues.put(DBHelper.STUDENT_ID_FK, term.getStudentId());

        return database.insert(DBHelper.TERM_TABLE_NAME , null, contentValues);
    }
}

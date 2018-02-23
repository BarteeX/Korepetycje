package com.example.monika.korepetycje.database.CRUDAdapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.DBHelper;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;
import com.example.monika.korepetycje.managers.TermManager;

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
        ContentValues contentValues = AdapterUtils.getContentValues(student);

        long studentId = database.insert(DBHelper.STUDENT_TABLE_NAME , null, contentValues);
        if (student.isNew()) {
            student.setId(studentId);
        }

        List<Address> addresses = student.getAddresses();
        for (int i = 0, addressesSize = addresses.size(); i < addressesSize; i++) {
            Address address = addresses.get(i);
            if (address.getStudentId() <= 0) {
                address.setStudentId(studentId);
            }

            long addressId = save(address);

            if (address.isNew()) {
                address.setId(addressId);
            }

            TermManager manager = TermManager.getInstance();
            List<Term> terms = manager.getTermsForAddress(address);

            for (int j = 0, termsSize = terms.size(); j < termsSize; j++) {
                Term term = terms.get(j);
                if (term.getAddressId() <= 0)
                    term.setAddressId(addressId);

                if (term.getStudentId() <= 0)
                    term.setStudentId(studentId);

                long termId = save(term);
            }
        }
        return studentId;
    }

    private long save(Address address) {
        System.out.println("SAVING ADDRESS = " + address.toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = AdapterUtils.getContentValues(address);
        return database.insert(DBHelper.ADDRESS_TABLE_NAME , null, contentValues);
    }

    private long save(Term term) {
        System.out.println("SAVING TERM = " + term.toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = AdapterUtils.getContentValues(term);
        return database.insert(DBHelper.TERM_TABLE_NAME , null, contentValues);
    }
}

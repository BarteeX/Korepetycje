package com.example.monika.korepetycje.database.CRUDAdapters;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.DBHelper;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

import java.util.List;


public class CreateAdapter extends Adapter {

    private static CreateAdapter instance = null;

    private CreateAdapter() {
        super();
    }

    public static CreateAdapter getInstance() {
        if(instance == null) {
            instance = new CreateAdapter();
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

        long studentId = database.insert(DBHelper.INSTANCE.getSTUDENT_TABLE_NAME(), null, contentValues);
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

            List<Term> terms = address.getTerms();

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
        return database.insert(DBHelper.INSTANCE.getADDRESS_TABLE_NAME(), null, contentValues);
    }

    private long save(Term term) {
        System.out.println("SAVING TERM = " + term.toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = AdapterUtils.getContentValues(term);
        return database.insert(DBHelper.INSTANCE.getTERM_TABLE_NAME(), null, contentValues);
    }
}

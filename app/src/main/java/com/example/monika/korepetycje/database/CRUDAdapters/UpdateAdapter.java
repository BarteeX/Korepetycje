package com.example.monika.korepetycje.database.CRUDAdapters;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.DBHelper;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

import java.util.List;


public class UpdateAdapter extends Adapter {

    private static UpdateAdapter instance = null;

    private UpdateAdapter() {
        super();
    }

    public static UpdateAdapter getInstance() {
        if(instance == null) {
            instance = new UpdateAdapter();
        }
        return instance;
    }

    public long update(DatabaseModel model) {
        if (model instanceof Address) {
            return update((Address) model);
        } else if (model instanceof Student) {
            return update((Student) model);
        } else if (model instanceof Term) {
            return update((Term) model);
        }
        return 0;
    }

    private long update(Student student) {
        System.out.println("UPDATING STUDENT = " + student.getName() + " " + student.getSurname());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = AdapterUtils.getContentValues(student);
        List<Address> addresses = student.getAddresses();

        for (int i = 0, addressesSize = addresses.size(); i < addressesSize; i++) {
            Address address = addresses.get(i);
            if (address.isNew()) {
                CreateAdapter createAdapter = CreateAdapter.getInstance();
                createAdapter.save(address);
            } else {
                update(address);
            }
        }

        List<Term> terms = student.getTerms();
        for (int i = 0, termsSize = terms.size(); i < termsSize; i++) {
            Term term = terms.get(i);
            if (term.isNew()) {
                CreateAdapter createAdapter = CreateAdapter.getInstance();
                createAdapter.save(term);
            } else {
                update(term);
            }
        }

        String[] whereArgs = {
                String.valueOf(student.getId())
        };

        return database.update (
                DBHelper.INSTANCE.getSTUDENT_TABLE_NAME(),
                contentValues,
                DBHelper.INSTANCE.getSTUDENT_ID_PK() + " = ?",
                whereArgs
        );
    }

    private long update(Address address) {
        System.out.println("UPDATING ADDRESS = " + address.toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues =  AdapterUtils.getContentValues(address);

        String[] whereArgs = {
                String.valueOf(address.getId())
        };

        return database.update (
                DBHelper.INSTANCE.getADDRESS_TABLE_NAME(),
                contentValues,
                DBHelper.INSTANCE.getADDRESS_ID_PK() + " = ?",
                whereArgs
        );
    }

    private long update(Term term) {
        System.out.println("UPDATING TERM = " + term.toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues =  AdapterUtils.getContentValues(term);

        String[] whereArgs = {
                String.valueOf(term.getId())
        };

        return database.update (
                DBHelper.INSTANCE.getTERM_TABLE_NAME(),
                contentValues,
                DBHelper.INSTANCE.getTERM_ID_PK() + " = ?",
                whereArgs
        );
    }
}

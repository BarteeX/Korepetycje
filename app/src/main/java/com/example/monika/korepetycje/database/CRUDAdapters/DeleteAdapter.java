package com.example.monika.korepetycje.database.CRUDAdapters;

import android.database.sqlite.SQLiteDatabase;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.DBHelper;
import com.example.monika.korepetycje.database.models.Address;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;


public class DeleteAdapter extends Adapter {

    private static DeleteAdapter instance = null;

    private DeleteAdapter() {
        super();
    }

    public static DeleteAdapter getInstance() {
        if(instance == null) {
            instance = new DeleteAdapter();
        }
        return instance;
    }

    public long delete(DatabaseModel model) {
        if (model instanceof Address) {
            return delete((Address)model);
        } else if (model instanceof Student) {
            return delete((Student)model);
        } else if (model instanceof Term) {
            return delete((Term)model);
        }
        return 0;
    }

    private long delete(Student student) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] whereArgs = {String.valueOf(student.getId())};

        int addressesCount = db.delete (
                DBHelper.INSTANCE.getADDRESS_TABLE_NAME(),
                DBHelper.INSTANCE.getSTUDENT_ID_FK() + " = ?",
                whereArgs
        );

        int termsCount = db.delete (
                DBHelper.INSTANCE.getTERM_TABLE_NAME(),
                DBHelper.INSTANCE.getSTUDENT_ID_FK() + " = ?",
                whereArgs
        );

        int studentsCount = db.delete (
                DBHelper.INSTANCE.getSTUDENT_TABLE_NAME(),
                DBHelper.INSTANCE.getSTUDENT_ID_PK() +" = ?" ,
                whereArgs);

        return addressesCount + termsCount + studentsCount;

    }

    private long delete(Term term) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] whereArgs = {String.valueOf(term.getId())};

        return db.delete (
                DBHelper.INSTANCE.getTERM_TABLE_NAME(),
                DBHelper.INSTANCE.getTERM_ID_PK() + " = ?",
                whereArgs
        );
    }

    private long delete(Address address) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] whereArgs = {String.valueOf(address.getId())};

        return db.delete (
                DBHelper.INSTANCE.getADDRESS_TABLE_NAME(),
                DBHelper.INSTANCE.getADDRESS_ID_PK() + " = ?",
                whereArgs
        );
    }
}

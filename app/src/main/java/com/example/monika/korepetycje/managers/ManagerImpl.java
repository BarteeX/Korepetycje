package com.example.monika.korepetycje.managers;

import android.content.Context;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.CRUDAdapters.ReadAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 2018-02-03.
 */

public abstract class ManagerImpl <T extends DatabaseModel> implements Manager <T>{

    protected List<T> list;

    ManagerImpl() {
        this.list = new ArrayList<>();
    }

    @Override
    public List<T> getAll(){
        return list;
    }

    @Override
    public T get(String idn){
        for (T dbo : list) {
            if (dbo.getIdn().equals(idn)) {
                return dbo;
            }
        }
        return null;
    }

    @Override
    public void add(T dbo){
        if (list.contains(dbo)) {
            update(dbo);
        } else {
            list.add(dbo);
        }
    }

    @Override
    public void remove(T dbo){
        list.remove(dbo);
    }

    @Override
    public void update(T dbo){
        remove(dbo);
        add(dbo);
    }

    @Override
    public void load(Context context){
        ReadAdapter adapter = ReadAdapter.getInstance(context);
        List<? extends DatabaseModel> dbObjects = adapter.get(this);
         if (dbObjects != null) {
             for (DatabaseModel dbObject : dbObjects) {
                 if (dbObject != null){
                     try {
                         list.add((T) dbObject);
                     } catch (ClassCastException e) {
                         e.printStackTrace();
                     }
                 }
             }
         }
    }
}

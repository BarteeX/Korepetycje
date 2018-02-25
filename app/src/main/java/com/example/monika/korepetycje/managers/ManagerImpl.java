package com.example.monika.korepetycje.managers;

import android.content.Context;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.GUI.Controllers.StudentsList;
import com.example.monika.korepetycje.GUI.StudentCard.StudentCardActivity;
import com.example.monika.korepetycje.database.CRUDAdapters.CreateAdapter;
import com.example.monika.korepetycje.database.CRUDAdapters.DeleteAdapter;
import com.example.monika.korepetycje.database.CRUDAdapters.ReadAdapter;
import com.example.monika.korepetycje.database.CRUDAdapters.UpdateAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class ManagerImpl <T extends DatabaseModel> implements Manager <T> {

    protected List<T> list;

    public ManagerImpl() {
        this.list = new ArrayList<T>(){
            @Override
            public boolean contains(Object value) {
                if (value instanceof DatabaseModel) {
                    long id = ((DatabaseModel)value).getId();
                    for (T dbo : this) {
                        if (dbo.getId() == id) {
                            return true;
                        }
                    }
                }
                return false;
            }

            public int indexOfById(long id) {
                for (int i = 0, listSize = list.size(); i < listSize; i++) {
                    T t = list.get(i);
                    if (t.getId() == id) {
                        return i;
                    }
                }
                return -1;
            }
        };
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
    public void save(T dbo){
        if (list.contains(dbo)) {
            update(dbo);
        } else {
            list.add(dbo);
            CreateAdapter adapter = CreateAdapter.getInstance();
            adapter.save(dbo);
        }
    }

    @Override
    public void delete(T dbo){
        list.remove(dbo);

        DeleteAdapter adapter = DeleteAdapter.getInstance();
        adapter.delete(dbo);
    }

    @Override
    public void update(T dbo){
        long dboId = dbo.getId();
        for (int i  = 0, length = list.size(); i < length; i++) {
            T object = list.get(i);
            if (object.getId() == dboId) {
                list.remove(i);
                list.add(dbo);
                break;
            }
        }
        UpdateAdapter adapter = UpdateAdapter.getInstance();
        adapter.update(dbo);
    }

    @Override
    public void load(){
        ReadAdapter adapter = ReadAdapter.getInstance();
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

    @Override
    public void deleteAll(List<T> collection) {
        for (int i = 0, collectionSize = collection.size(); i < collectionSize; i++) {
            T dbo = collection.get(i);
            delete(dbo);
        }
    }
}

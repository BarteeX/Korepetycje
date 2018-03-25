package com.example.monika.korepetycje.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.GUI.Controllers.StudentsList;
import com.example.monika.korepetycje.GUI.StudentCard.StudentCardActivity;
import com.example.monika.korepetycje.database.CRUDAdapters.CreateAdapter;
import com.example.monika.korepetycje.database.CRUDAdapters.DeleteAdapter;
import com.example.monika.korepetycje.database.CRUDAdapters.ReadAdapter;
import com.example.monika.korepetycje.database.CRUDAdapters.UpdateAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class ManagerImpl <T extends DatabaseModel> implements Manager <T> {

    protected List<T> list;

    ManagerImpl() {
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
        };
    }

    public void deleteAll() {
        list.clear();
    }

    @Override
    public List<T> getAll(){
        return new ArrayList<>(list);
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
                         list.add((T)dbObject);
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

    @Override
    public List<T> getByIds(@Nullable long... args) {
        List<T> accepted = new ArrayList<>();
        if (args != null && args.length > 0) {
            List ids = Arrays.asList(args);
            for (T object : getAll()) {
                if (ids.contains(object.getId())) {
                    accepted.add(object);
                }
            }
        } else {
            accepted = getAll();
        }
        return accepted;
    }

    @Override
    public List<T> filter(@NonNull String filter, @Nullable long... args) {
        List<T> accepted = new ArrayList<>();
        List<T> listToFilter =  getByIds(args);
        if (list.size() > 0) {
            Field[] declaredFields = list.get(0).getClass().getDeclaredFields();

            for (T object : listToFilter) {
                for (Field field : declaredFields) {
                    try {
                        String value = (String) field.get(object);
                        if (value != null && value.toLowerCase().contains(filter.toLowerCase())) {
                            accepted.add(object);
                            break;
                        }
                    } catch (Exception e) {
                        //ignore
                    }
                }
            }
        }
        return accepted;
    }
}

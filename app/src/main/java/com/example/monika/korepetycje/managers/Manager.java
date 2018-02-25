package com.example.monika.korepetycje.managers;

import android.content.Context;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.models.Student;

import java.util.Collection;
import java.util.List;

/**
 * Created by Monika on 2018-01-27.
 */

public interface Manager<T extends DatabaseModel> {

    public List<T> getAll();

    public T get(String idn);

    public void save(T dbo);

    public void delete(T dbo);

    public void deleteAll(List<T> collection);

    public void update(T dbo);

    public void load();

}

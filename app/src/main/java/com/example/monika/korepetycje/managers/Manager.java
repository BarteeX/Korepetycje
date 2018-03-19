package com.example.monika.korepetycje.managers;

import com.example.monika.korepetycje.DatabaseModel;

import java.util.List;


public interface Manager<T extends DatabaseModel> {

    List<T> getAll();

    T get(String idn);

    void save(T dbo);

    void delete(T dbo);

    void deleteAll(List<T> collection);

    void update(T dbo);

    void load();

    List<T> filter(String filter, long... args);

    List<T> getByIds(long... args);


}

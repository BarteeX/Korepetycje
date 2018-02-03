package com.example.monika.korepetycje.managers;

import android.content.Context;

import com.example.monika.korepetycje.DatabaseModel;
import com.example.monika.korepetycje.database.models.Student;

import java.util.List;

/**
 * Created by Monika on 2018-01-27.
 */

public interface Manager<T> {
    //TODO: PRZEROBI NA KLASE ABSTRAKCYJNĄ

    public List<T> getAll();

    public T get(String idn);

    public void add(T dbo);

    public void remove(T dbo);

    public void update(T dbo);

    public void load(Context context, Manager<T> manager);
}

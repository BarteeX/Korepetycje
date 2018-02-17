package com.example.monika.korepetycje;

import android.content.Context;

import com.example.monika.korepetycje.database.CRUDAdapters.CreateAdapter;
import com.example.monika.korepetycje.managers.Manager;

/**
 * Created by Monika on 2018-01-27.
 */

public abstract class DatabaseModel {

    protected Manager manager = null;
    protected String idn;
    protected long id;
    boolean isNew = true;


    public DatabaseModel(Manager manager) {
        this.manager = manager;
    }

    public boolean isNew() {
        return isNew;
    }

    public void isLoaded() {
        isNew = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdn() {
        return idn;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public void save(Context context) {
        manager.add(this);

        CreateAdapter adapter = CreateAdapter.getInstance(context);
        adapter.save(this);
        isNew = false;
    };

    public void delete() {
        manager.remove(this);
    };

    public void update(){
        manager.update(this);
    };
}

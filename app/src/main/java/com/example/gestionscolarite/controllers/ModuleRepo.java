package com.example.gestionscolarite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.gestionscolarite.models.Module;

public class ModuleRepo {
    private MyDatabaseHelper myDatabaseHelper;

    public ModuleRepo(Context context){
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    public int insert(Module module){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Module.COLUMN_NOM, module.getNom_module());

        long module_id = db.insert(Module.TABLE, null, values);

        return  (int) module_id;
    }

}

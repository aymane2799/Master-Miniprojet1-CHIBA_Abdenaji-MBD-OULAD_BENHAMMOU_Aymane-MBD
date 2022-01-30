package com.example.gestionscolarite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.gestionscolarite.models.Filiere;
import com.example.gestionscolarite.models.Module;

public class FiliereRepo {
    private final MyDatabaseHelper myDatabaseHelper;

    public FiliereRepo(Context context){
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    public int insert(Filiere filiere){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Filiere.COLUMN_NOM, filiere.getNom_filiere());

        long filiere_id = db.insert(Filiere.TABLE, null, values);
        db.close();

        return (int) filiere_id;
    }

    Cursor getAll(){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        String query = "Select * from " + Filiere.TABLE;

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
}

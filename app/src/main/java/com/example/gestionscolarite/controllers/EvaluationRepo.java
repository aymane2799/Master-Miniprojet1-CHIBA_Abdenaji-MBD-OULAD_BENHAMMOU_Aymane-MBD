package com.example.gestionscolarite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.gestionscolarite.models.Filiere;

public class EvaluationRepo {
    private final MyDatabaseHelper myDatabaseHelper;

    public EvaluationRepo(Context context){
        myDatabaseHelper = new MyDatabaseHelper(context);
    }


}

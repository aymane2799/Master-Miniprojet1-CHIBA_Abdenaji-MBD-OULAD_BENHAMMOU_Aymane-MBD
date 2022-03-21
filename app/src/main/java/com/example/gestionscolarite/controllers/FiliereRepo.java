package com.example.gestionscolarite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.example.gestionscolarite.activities.UpdateFiliereActivity;
import com.example.gestionscolarite.models.Filiere;
import com.example.gestionscolarite.models.Module;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public int destroy(String row_id){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        int deleted = 0;
        if(db != null){
            deleted = db.delete(Filiere.TABLE, Filiere.COLUMN_ID + "=?" ,new String[]{row_id});
        }
        db.close();

        Log.d("DELETE", String.valueOf(deleted));

        return deleted;
    }


    public ArrayList<Filiere> getAll(){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        ArrayList<Filiere> filieresList = new ArrayList<Filiere>();
        String query = "Select * from " + Filiere.TABLE;

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.moveToFirst()) {
            do {
                Filiere filiere = new Filiere();
                filiere.setId_filiere(Integer.valueOf(cursor.getString(0)));
                filiere.setNom_filiere(cursor.getString(1));
                filieresList.add(filiere);
            } while (cursor.moveToNext());
        }
        db.close();

        return filieresList;
    }

    public ArrayList<String> getAllNames(){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();

        ArrayList<String> filieresList = new ArrayList<>();

        String query = "SELECT * FROM "+Filiere.TABLE;

        Cursor cursor = null;

        if( db!=null){
            cursor = db.rawQuery(query, null);
        }

        if(cursor.moveToFirst()){
            do {
                filieresList.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        db.close();

        return  filieresList;
    }

    public Integer getAllCount(){
        return getAll().size();
    }

    public long updateFiliere(String row_id, String nomFiliere){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Filiere.COLUMN_NOM, nomFiliere);

        long result = 0;
        if(db != null){
            result = db.update(Filiere.TABLE, contentValues, Filiere.COLUMN_ID + "=?", new String[]{row_id});
            Log.d("LOG", String.valueOf(result) + " - ID : " + row_id + " - New Name : " + nomFiliere);
        }
        db.close();

        return result;
    }

    public Integer getModulesCount(String filiereId){
        Integer modules_count = 0;
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();

        String query = "SELECT distinct module_id from plannings WHERE filiere_id="+ filiereId + ";";

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        Log.d("CURSOR ", String.valueOf(cursor.getCount()));
        modules_count = cursor.getCount();

        return modules_count;
    }



    public ArrayList<Integer> getAllModulesCount(){
        ArrayList<Integer> module_count = new ArrayList<>(Collections.nCopies(this.getAllCount(),0));

        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        String query = "SELECT count(module_id) from plannings group by filiere_id;";

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                module_count.set(i, cursor.getInt(0));
                i++;
            } while (cursor.moveToNext());
        }

        db.close();

        return  module_count;
    }

    public long associateModules(String filiere_id, ArrayList<Integer> ids){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(db != null){
            long deleted = db.delete("plannings", "filiere_id=?", new String[]{filiere_id});
        }
        long planning_id = 0;
        Log.d("IDs Size", String.valueOf(ids.size()));

        for (int i=0; i<ids.size(); i++){
            values.put("filiere_id", filiere_id);
            values.put("module_id", ids.get(i));
            planning_id = db.insert("plannings", null, values);
            Log.d("Inserted", String.valueOf(planning_id));
        }

        return planning_id;
    }

    public ArrayList<Module> modules(int filiereID){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        ArrayList<Module> modules = new ArrayList<>();

        String query = "SELECT * from modules m JOIN plannings p ON m._id=p.module_id WHERE p.filiere_id=" + filiereID + ";";

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.moveToFirst()) {
            do {
                Module module = new Module();
                module.setId_module(Integer.valueOf(cursor.getString(0)));
                module.setNom_module(cursor.getString(1));
                modules.add(module);
            } while (cursor.moveToNext());
        }
        db.close();

        return modules;
    }
}

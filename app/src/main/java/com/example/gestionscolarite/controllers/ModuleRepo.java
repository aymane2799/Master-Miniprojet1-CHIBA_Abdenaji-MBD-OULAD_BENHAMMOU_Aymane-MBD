package com.example.gestionscolarite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.gestionscolarite.models.Filiere;
import com.example.gestionscolarite.models.Module;

import java.util.ArrayList;
import java.util.List;

public class ModuleRepo {
    private final MyDatabaseHelper myDatabaseHelper;

    public ModuleRepo(Context context){
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    public int insert(Module module){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Module.COLUMN_NOM, module.getNom_module());

        long module_id = db.insert(Module.TABLE, null, values);

//        if(filiere != null){
//        ContentValues planningValues = new ContentValues();
//        planningValues.put("module_id", module.getId_module());
//        planningValues.put("filiere_id", filiere.getId_filiere());
//        long planning_id = db.insert("plannings", null, planningValues);
//        }

        db.close();

        return  (int) module_id;
    }

    public long updateModule(String row_id, String nomModule){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Module.COLUMN_NOM, nomModule);
        long result = 0;
        if(db != null){
            result = db.update(Module.TABLE, contentValues,Filiere.COLUMN_ID + "=?", new String[]{row_id});
        }
        db.close();

        return result;
    }

    public Module getModuleFromId(int id){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Module module = new Module();
        String query = "Select * from modules WHERE _id="+id;
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        if (cursor.moveToFirst()) {
                module.setId_module(Integer.valueOf(cursor.getString(0)));
                module.setNom_module(cursor.getString(1));
        }

        return module;
    }

    public ArrayList<Module> getAll(){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();

        ArrayList<Module> modulesList = new ArrayList<Module>();

        String query = "Select * from " + Module.TABLE;

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.moveToFirst()) {
            do {
                Module module = new Module();
                module.setId_module(Integer.valueOf(cursor.getString(0)));
                module.setNom_module(cursor.getString(1));
                modulesList.add(module);
            } while (cursor.moveToNext());
        }

        return modulesList;
    }



    public Cursor getAllCursor(){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();

        ArrayList<Module> modulesList = new ArrayList<Module>();

        String query = "Select * from " + Module.TABLE;

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public  ArrayList<String> getAllNames(){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();

        ArrayList<String> modulesList = new ArrayList<>();

        String query = "Select * from " + Module.TABLE;

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.moveToFirst()) {
            do {
//                module.setId_module(Integer.valueOf(cursor.getString(0)));
//                module.setNom_module(cursor.getString(1));
                modulesList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return modulesList;
    }

    public ArrayList<Integer> getAllIds(){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();

        ArrayList<Integer> modulesList = new ArrayList<>();

        String query = "Select * from " + Module.TABLE;

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.moveToFirst()) {
            do {
//                module.setId_module(Integer.valueOf(cursor.getString(0)));
//                module.setNom_module(cursor.getString(1));
                modulesList.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        return modulesList;
    }

    public ArrayList<Filiere> filieres(int moduleId){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        ArrayList<Filiere> filieres = new ArrayList<>();

        String query = "SELECT * from filieres f JOIN plannings p ON f._id=p.filiere_id WHERE p.module_id=" + moduleId + ";";

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.moveToFirst()) {
            do {
                Filiere filiere = new Filiere();
                filiere.setId_filiere(Integer.valueOf(cursor.getString(0)));
                filiere.setNom_filiere(cursor.getString(1));
                filieres.add(filiere);
            } while (cursor.moveToNext());
        }
        db.close();

        return filieres;
    }

    public long associateToFiliere(String module_id, ArrayList<Integer> ids){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (db != null){
            long deleted = db.delete("plannings", "module_id=?", new String[]{module_id});
        }

        long planning_id = 0;
        for (int i=0; i<ids.size(); i++){
            values.put("module_id", module_id);
            values.put("filiere_id", ids.get(i));
            planning_id = db.insert("plannings", null, values);
            Log.d("Inserted", String.valueOf(planning_id));
        }

        return planning_id;    }

    public Integer getAllCount(){
        return getAll().size();
    }

}

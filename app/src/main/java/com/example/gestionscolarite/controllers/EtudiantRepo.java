package com.example.gestionscolarite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.gestionscolarite.models.Etudiant;
import com.example.gestionscolarite.models.Filiere;

import java.util.ArrayList;

public class EtudiantRepo {
    final MyDatabaseHelper myDatabaseHelper;

    public EtudiantRepo(Context context){
        myDatabaseHelper= new MyDatabaseHelper(context);
    }

    public int insert(Etudiant etudiant){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Etudiant.COLUMN_CNE, etudiant.getCne());
        contentValues.put(Etudiant.COLUMN_NOM, etudiant.getNom());
        contentValues.put(Etudiant.COLUMN_PRENOM, etudiant.getPrenom());
        contentValues.put(Etudiant.COLUMN_NIVEAU, etudiant.getNiveau());
        contentValues.put(Etudiant.COLUMN_NOM, etudiant.getNom());

        long etudiant_id = 0;
        if (db != null){
            etudiant_id = db.insert(Etudiant.TABLE, null, contentValues);
        }

        Log.d("etudiantId", String.valueOf(etudiant_id));
        db.close();

        return (int) etudiant_id;
    }

    public  int destroy(String row_id){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();

        int deleted = 0;

        if(db != null){
            deleted = db.delete(Etudiant.TABLE, Etudiant.COLUMN_ID + "=?", new String[]{row_id});
        }
        db.close();

        return deleted;
    }

    public ArrayList<Etudiant> getAll(){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        ArrayList<Etudiant> etudiants = new ArrayList<>();
        String query = "SELECT * FROM " + Etudiant.TABLE;

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        if(cursor.moveToFirst()){
            do {
                Etudiant etudiant = new Etudiant();
                etudiant.setId_etudiant(Integer.valueOf(cursor.getString(0)));
                etudiant.setCne(cursor.getString(1));
                etudiant.setNom(cursor.getString(2));
                etudiant.setPrenom(cursor.getString(3));
                etudiant.setNiveau(cursor.getString(4));
                etudiants.add(etudiant);
            }while (cursor.moveToNext());
        }
        db.close();
        Log.d("Etudiants", etudiants.toString());
        return etudiants;
    }

    public ArrayList<String> getAllCnes(){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        ArrayList<String> cneList = new ArrayList<>();
        String query = "SELECT cne FROM " + Etudiant.TABLE;

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        if(cursor.moveToFirst()){
            do {
                cneList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        db.close();

        return cneList;
    }

    public Integer getAllCount(){
        return getAll().size();
    }

    public long updateEtudiant(String row_id, Etudiant etudiant){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Etudiant.COLUMN_CNE, etudiant.getCne());
        contentValues.put(Etudiant.COLUMN_NOM, etudiant.getNom());
        contentValues.put(Etudiant.COLUMN_PRENOM, etudiant.getPrenom());
        contentValues.put(Etudiant.COLUMN_NIVEAU, etudiant.getNiveau());

        long result = 0;
        if(db != null){
            result = db.update(Etudiant.TABLE, contentValues, Etudiant.COLUMN_ID + "=?" , new String[]{row_id});
        }
        db.close();

        return result;
    }

    public long associateToFiliere(String row_id, String filiere_id){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Etudiant.COLUMN_FILIERE_ID, Integer.valueOf(filiere_id));
        long result = 0;
        if (db != null){
            result = db.update(Etudiant.TABLE, contentValues, Etudiant.COLUMN_ID + "=?", new String[]{row_id});
        }
        db.close();

        return result;
    }

    public Filiere filiere(int EtudiantId){
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Filiere filiere = new Filiere();
        String query = "SELECT * FROM filieres WHERE _id=" + EtudiantId;

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        if(cursor.moveToFirst()){
            filiere.setId_filiere(Integer.valueOf(cursor.getString(0)));
            filiere.setNom_filiere(cursor.getString(1));
        }

        return filiere;
    }

}

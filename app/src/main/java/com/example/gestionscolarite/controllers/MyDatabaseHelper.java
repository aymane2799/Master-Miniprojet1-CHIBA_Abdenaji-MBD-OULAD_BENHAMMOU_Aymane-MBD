package com.example.gestionscolarite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.gestionscolarite.models.Etudiant;
import com.example.gestionscolarite.models.Filiere;
import com.example.gestionscolarite.models.Module;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    // Declaration des variables
    private Context context;

    // Initialisation des variables

    //  Nom de la base de données
    private static final String DATABASE_NAME = "Scolarite";
    //  Version
    private static final int DATABASE_VERSION = 1;
    //  Nom des tables
    private static final String TABLE_ETUDIANTS = "etudiants";
    private static final String TABLE_FILIERES = "filieres";
    private static final String TABLE_EVALUATIONS = "evaluations";
    private static final String TABLE_PLANINGS = "plannings";

    //  Champs des Tables

        // etudiants
    private static final String ETUDIANT_ID = "_id";
    private static final String ETUDIANT_CNE = "cne_etudiant";
    private static final String ETUDIANT_NOM = "nom_etudiant";
    private static final String ETUDIANT_PRENOM = "prenom_etudiant";
    private static final String ETUDIANT_NIVEAU = "niveau";
    private static final String ETUDIANT_ID_FILIERE = "filiere_id";

        // evaluations
    private static final String EVALUATION_ID = "_id";
    private static final String EVALUATION_ID_ETUDIANT = "id_etudiant";
    private static final String EVALUATION_ID_MODULE = "id_module";
    private static final String EVALUATION_NOTE = "note_evaluation";

        // planings
    private static final String PLANNING_ID = "_id";
    private static final String PLANNING_ID_MODULE = "id_module";
    private static final String PLANNING_ID_FILIERE = "id_filiere";
    private static final String PLANNING_NIVEAU = "niveau";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creation des tables
        String modules = "CREATE TABLE " + Module.TABLE
                + " ("+ Module.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ Module.COLUMN_NOM +" TEXT);";

        String filieres = "CREATE TABLE " + TABLE_FILIERES
                + " ("+ Filiere.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ Filiere.COLUMN_NOM +" TEXT);";

        Log.d("DATABASE" , "Creating etudiants");
        String etudiants = "CREATE TABLE " + Etudiant.TABLE
                + " ("+ Etudiant.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ Etudiant.COLUMN_CNE +" TEXT,"
                + Etudiant.COLUMN_NOM +" TEXT,"+ Etudiant.COLUMN_PRENOM +" TEXT," + Etudiant.COLUMN_NIVEAU + " TEXT,filiere_id INTEGER, FOREIGN KEY (filiere_id) REFERENCES  "+
                Filiere.TABLE +"(" + Filiere.COLUMN_ID + "));";

//        String evaluations = "CREATE TABLE " + TABLE_EVALUATIONS
//                + " ("+ EVALUATION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ EVALUATION_NOTE +" DECIMAL(4,2), FOREIGN KEY ("
//                + ETUDIANT_ID + ") REFERENCES "+ TABLE_ETUDIANTS +"("+ ETUDIANT_ID +")"+" , FOREIGN KEY (module_id) " +
//                "REFERENCES "+ Module.TABLE +"("+ Module.COLUMN_ID +"));";

        String plannings = "CREATE TABLE " + TABLE_PLANINGS
                + " ("+ PLANNING_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ PLANNING_NIVEAU +" INTEGER, filiere_id INTEGER, module_id INTEGER,"
                +" FOREIGN KEY (filiere_id) REFERENCES " + Filiere.TABLE +"("+ Filiere.COLUMN_ID +"),"
                +" FOREIGN KEY (module_id) REFERENCES " + Module.TABLE +"("+ Module.COLUMN_ID +"));";

        sqLiteDatabase.execSQL(modules);
        sqLiteDatabase.execSQL(filieres);
        sqLiteDatabase.execSQL(etudiants);
        sqLiteDatabase.execSQL(plannings);
//        sqLiteDatabase.execSQL(evaluations);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //  Drop tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Module.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Filiere.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Etudiant.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUATIONS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANINGS);

        onCreate(sqLiteDatabase);
    }
}

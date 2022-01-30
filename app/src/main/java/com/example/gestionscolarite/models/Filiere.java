package com.example.gestionscolarite.models;

public class Filiere {
    //  Nom des tables
    public static final String TABLE = "filieres";
    //  Champs des Tables
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOM = "nom_filiere";


    private Integer id_filiere;
    private String nom_filiere;

    public Filiere() {
    }

    public Integer getId_filiere() {
        return id_filiere;
    }

    public void setId_filiere(Integer id_filiere) {
        this.id_filiere = id_filiere;
    }

    public String getNom_filiere() {
        return nom_filiere;
    }

    public void setNom_filiere(String nom_filiere) {
        this.nom_filiere = nom_filiere;
    }

    @Override
    public String toString() {
        return "Filiere{" +
                "id_filiere=" + id_filiere +
                ", nom_filiere='" + nom_filiere + '\'' +
                '}';
    }
}

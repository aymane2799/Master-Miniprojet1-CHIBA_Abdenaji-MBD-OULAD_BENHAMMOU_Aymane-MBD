package com.example.gestionscolarite.models;

public class Etudiant {

    //  Nom des tables
    public static final String TABLE = "etudiants";
    //  Champs des Tables
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CNE = "cne";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_PRENOM = "prenom";
    public static final String COLUMN_NIVEAU = "niveau";
    public static final String COLUMN_FILIERE_ID = "filiere_id";

    Integer id_etudiant, filiere_id;
    String cne, nom, prenom, niveau;

    public Etudiant() {
    }

    public Integer getId_etudiant() {
        return id_etudiant;
    }

    public Integer getFiliere_id() {
        return filiere_id;
    }

    public String getCne() {
        return cne;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setId_etudiant(Integer id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public void setFiliere_id(Integer filiere_id) {
        this.filiere_id = filiere_id;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id_etudiant=" + id_etudiant +
                ", filiere_id=" + filiere_id +
                ", cne='" + cne + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", niveau='" + niveau + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Etudiant etudiant = (Etudiant) o;

        if (!getId_etudiant().equals(etudiant.getId_etudiant())) return false;
        if (!getFiliere_id().equals(etudiant.getFiliere_id())) return false;
        if (!getCne().equals(etudiant.getCne())) return false;
        if (!getNom().equals(etudiant.getNom())) return false;
        if (!getPrenom().equals(etudiant.getPrenom())) return false;
        return getNiveau().equals(etudiant.getNiveau());
    }
}

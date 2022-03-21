package com.example.gestionscolarite.models;

public class Module {

    //  Nom des tables
    public static final String TABLE = "modules";
    //  Champs des Tables
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOM = "nom_module";


    private Integer id_module;
    private String nom_module;

    public Module() {
    }

    public Module(String nom_module) {
        this.nom_module = nom_module;
    }

    public Module(Integer id_module, String nom_module) {
        this.id_module = id_module;
        this.nom_module = nom_module;
    }

    public Integer getId_module() {
        return id_module;
    }

    public void setId_module(Integer id_module) {
        this.id_module = id_module;
    }

    public String getNom_module() {
        return nom_module;
    }

    public void setNom_module(String nom_module) {
        this.nom_module = nom_module;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id_module=" + id_module +
                ", nom_module='" + nom_module + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Module module = (Module) o;

        if (!getId_module().equals(module.getId_module())) return false;
        return getNom_module().equals(module.getNom_module());
    }

}

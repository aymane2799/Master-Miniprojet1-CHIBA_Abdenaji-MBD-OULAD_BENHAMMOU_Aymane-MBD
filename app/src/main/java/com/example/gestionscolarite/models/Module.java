package com.example.gestionscolarite.models;

public class Module {
    Integer id_module;
    String nom_module;

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


}

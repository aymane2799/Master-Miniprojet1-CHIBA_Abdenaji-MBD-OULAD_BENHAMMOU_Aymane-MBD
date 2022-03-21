package com.example.gestionscolarite.activities;

import android.content.Intent;
import android.util.Log;
import android.util.LogPrinter;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.adapters.ModuleAdapter;
import com.example.gestionscolarite.controllers.ModuleRepo;
import com.example.gestionscolarite.models.Filiere;
import com.example.gestionscolarite.models.Module;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ModulesActivity extends AppCompatActivity {

    RecyclerView modulesRecyclerView;
    FloatingActionButton modulesActionButton;
    ImageButton moduleDeleteBtn;

    ModuleAdapter moduleAdapter;
    ModuleRepo moduleRepo;
    ArrayList<String> modules_id, modules_nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        modulesRecyclerView = findViewById(R.id.modulesRecyclerView);
        moduleRepo = new ModuleRepo(ModulesActivity.this);


        modulesActionButton = findViewById(R.id.modulesActionButton);
        modulesActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(ModulesActivity.this, AddModuleActivity.class);
            startActivity(intent);
        });

        modules_id = new ArrayList<>();
        modules_nom = new ArrayList<>();

        storeDataInArrays();

        moduleAdapter = new ModuleAdapter(ModulesActivity.this,this, modules_id, modules_nom);
        modulesRecyclerView.setAdapter(moduleAdapter);
        modulesRecyclerView.setLayoutManager(new LinearLayoutManager(ModulesActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        List<Module> modules = moduleRepo.getAll();

        if(modules.size() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            for(int i = 0; i< modules.size(); i++){
                modules_id.add(String.valueOf(modules.get(i).getId_module()));
                modules_nom.add(modules.get(i).getNom_module());
            }
        }
    }
}
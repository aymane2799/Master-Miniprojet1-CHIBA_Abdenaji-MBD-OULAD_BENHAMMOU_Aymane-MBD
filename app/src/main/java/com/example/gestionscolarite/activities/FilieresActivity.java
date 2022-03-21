package com.example.gestionscolarite.activities;

import android.content.Intent;
import android.database.Cursor;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.adapters.FiliereAdapter;
import com.example.gestionscolarite.controllers.FiliereRepo;
import com.example.gestionscolarite.controllers.MyDatabaseHelper;
import com.example.gestionscolarite.models.Filiere;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FilieresActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton filieresActionButton;
    ImageButton filiereDeleteBtn;

    FiliereAdapter filiereAdapter;
    FiliereRepo filRepo;
    ArrayList<String> filieres_id, filieres_nom;
    ArrayList<Integer> modules_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filieres);

        recyclerView = findViewById(R.id.filieresRecyclerView);
        filRepo = new FiliereRepo(this);

        filieresActionButton = findViewById(R.id.filieresActionButton);
        filieresActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(FilieresActivity.this, AddFiliereActivity.class);
            startActivity(intent);
        });

        filieres_id = new ArrayList<>();
        filieres_nom = new ArrayList<>();
        modules_count = new ArrayList<>();

        storeDataInArrays();

        filiereAdapter = new FiliereAdapter(FilieresActivity.this,this, filieres_id, filieres_nom, modules_count);
        recyclerView.setAdapter(filiereAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        List<Filiere> filieres = filRepo.getAll();

        if(filieres.size() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            for(int i = 0; i< filieres.size(); i++){
                filieres_id.add(String.valueOf(filieres.get(i).getId_filiere()));
                filieres_nom.add(filieres.get(i).getNom_filiere());
                modules_count.add(filRepo.getModulesCount(filieres.get(i).getId_filiere().toString()));
            }
        }
    }
}
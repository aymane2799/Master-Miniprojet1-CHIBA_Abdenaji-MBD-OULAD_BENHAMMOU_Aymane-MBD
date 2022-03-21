package com.example.gestionscolarite.activities;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.adapters.EtudiantAdapter;
import com.example.gestionscolarite.controllers.EtudiantRepo;
import com.example.gestionscolarite.controllers.FiliereRepo;
import com.example.gestionscolarite.models.Etudiant;
import com.example.gestionscolarite.models.Filiere;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EtudiantsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton etudiantsActionButton;

    EtudiantAdapter etudiantAdapter;
    EtudiantRepo etudiantRepo;
    ArrayList<String> etudiants_id, etudiants_cne, etudiants_nom, etudiants_prenom, etudiants_niveau, etudiants_filiereName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiants);

        recyclerView = findViewById(R.id.etudiantsRecyclerView);
        etudiantRepo = new EtudiantRepo(this);

        etudiantsActionButton = findViewById(R.id.etudiantsActionButton);
        etudiantsActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EtudiantsActivity.this,AddEtudiantActivity.class );
                startActivity(intent);
            }
        });

        etudiants_id = new ArrayList<>();
        etudiants_cne = new ArrayList<>();
        etudiants_nom = new ArrayList<>();
        etudiants_prenom = new ArrayList<>();
        etudiants_niveau = new ArrayList<>();
        etudiants_filiereName = new ArrayList<>();

        storeDataInArrays();

        etudiantAdapter = new EtudiantAdapter(EtudiantsActivity.this,this, etudiants_id, etudiants_cne, etudiants_nom, etudiants_prenom, etudiants_niveau, etudiants_filiereName);
        recyclerView.setAdapter(etudiantAdapter);
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
        ArrayList<Etudiant> etudiants = etudiantRepo.getAll();

        if (etudiants.size() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            for (int i=0; i < etudiants.size(); i++){
                etudiants_id.add(String.valueOf(etudiants.get(i).getId_etudiant()));
                etudiants_cne.add(etudiants.get(i).getCne());
                etudiants_nom.add(etudiants.get(i).getNom());
                etudiants_prenom.add(etudiants.get(i).getPrenom());
                etudiants_niveau.add(etudiants.get(i).getNiveau());
                etudiants_filiereName.add(etudiantRepo.filiere(etudiants.get(i).getId_etudiant()).getNom_filiere());
            }
        }
    }
}
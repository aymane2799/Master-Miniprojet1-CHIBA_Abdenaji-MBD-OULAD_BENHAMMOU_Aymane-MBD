package com.example.gestionscolarite.activities;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.controllers.FiliereRepo;
import com.example.gestionscolarite.controllers.ModuleRepo;
import com.example.gestionscolarite.models.Module;

import java.util.ArrayList;
import java.util.List;

public class UpdateFiliereActivity extends AppCompatActivity{

    FiliereRepo filiereRepo;

    EditText nomFiliereInput;
    Button updateFiliereBtn;

    String id, nom;

    TextView modulesTv2;
    ModuleRepo moduleRepo;
    boolean[] selectedModules;
    ArrayList<Integer> modulesPositions = new ArrayList<>();
    ArrayList<Integer> modulesId = new ArrayList<>();
    ArrayList<String> modulesN;
    CharSequence[] modulesNames;
    ArrayList<Module> modules, filiereModules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_filiere);

        filiereRepo = new FiliereRepo(this);
        moduleRepo = new ModuleRepo(this);

        modulesTv2 = findViewById(R.id.filieresTv2);
        modules = moduleRepo.getAll();
        modulesN = moduleRepo.getAllNames();
        selectedModules = new boolean[modulesN.size()];

        modulesNames = modulesN.toArray(new CharSequence[modulesN.size()]);
        nomFiliereInput = findViewById(R.id.nomFiliereInput2);

        getAndSetIntentData();

        Log.d("FiliereID", id);
        filiereModules = filiereRepo.modules(Integer.valueOf(id));
        Log.d("FiliereModules", filiereModules.toString());

        for(int i =0; i<modules.size(); i++){
               if(filiereModules.contains(modules.get(i))){
                   Log.d("found", String.valueOf(true));
                   selectedModules[i] = true;
               }
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < filiereModules.size(); j++){
            stringBuilder.append(filiereModules.get(j).getNom_module());
            if (j != filiereModules.size()-1){
                stringBuilder.append(", ");
            }
        }
        modulesTv2.setText(stringBuilder.toString());



        modulesTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateFiliereActivity.this);
                builder.setTitle("Associer les modules de la filiere : " + nom);

//                builder.setCancelable(false);

                builder.setMultiChoiceItems(modulesNames, selectedModules, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            modulesPositions.add(position);
                            modulesId.add(modules.get(position).getId_module());
                        }else {
                            modulesPositions.remove(Integer.valueOf(position));
                            modulesId.remove(Integer.valueOf(modules.get(position).getId_module()));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int j = 0; j < modulesPositions.size(); j++){
                            stringBuilder.append(modules.get(modulesPositions.get(j)).getNom_module());
                            if (j != modulesPositions.size()-1){
                                stringBuilder.append(", ");
                            }
                        }
                        modulesTv2.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("Nettoyer tout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int j =0; j<selectedModules.length; j++) {
                            selectedModules[j] = false;
                            modulesPositions.clear();
                            modulesId.clear();
                        }
                        modulesTv2.setText("");
                    }
                });

                builder.show();

            }
        });


//        modulesSpinner = findViewById(R.id.modulesSpinner);


        updateFiliereBtn = findViewById(R.id.updateModuleBtn);
        updateFiliereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFiliere();
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("idFiliere") && getIntent().hasExtra("nomFiliere")){
            // Getting Data
            id = getIntent().getStringExtra("idFiliere");
            nom = getIntent().getStringExtra("nomFiliere");

            //Setting Data
            nomFiliereInput.setText(nom);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void updateFiliere(){
        String nomFiliere = nomFiliereInput.getText().toString().trim();

        long updated = filiereRepo.updateFiliere(id, nomFiliere);
        if (updated == -1){
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            filiereRepo.associateModules(id, modulesId);
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

}
package com.example.gestionscolarite.activities;

import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.controllers.FiliereRepo;
import com.example.gestionscolarite.controllers.ModuleRepo;
import com.example.gestionscolarite.models.Filiere;
import com.example.gestionscolarite.models.Module;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddFiliereActivity extends AppCompatActivity {

    FiliereRepo filiereRepo;


    EditText nomFiliereInput;
    Button addFiliereBtn;

    TextView modulesTv;
    ModuleRepo moduleRepo;
    boolean[] selectedModules;
    ArrayList<Integer> modulesPositions = new ArrayList<>();
    ArrayList<Integer> modulesId = new ArrayList<>();
    ArrayList<String> modulesN;
    CharSequence[] modulesNames;
    ArrayList<Module> modules;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filiere);


        filiereRepo = new FiliereRepo(AddFiliereActivity.this);
        moduleRepo = new ModuleRepo(this);
        modulesTv = findViewById(R.id.modulesTv);
        modules = moduleRepo.getAll();
        modulesN = moduleRepo.getAllNames();
        selectedModules = new boolean[modulesN.size()];

        modulesNames = modulesN.toArray(new CharSequence[modulesN.size()]);

        modulesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Dialog", "Clicked !");
                AlertDialog.Builder builder = new AlertDialog.Builder(AddFiliereActivity.this);
                builder.setTitle("Associer les modules de la filiere");
//                builder.setCancelable(false);
//
                builder.setMultiChoiceItems(modulesNames, selectedModules, new OnMultiChoiceClickListener() {
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
                        modulesTv.setText(stringBuilder.toString());
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
                        modulesTv.setText("");
                    }
                });
            builder.show();
            }
        });


        nomFiliereInput = findViewById(R.id.nomFiliereInput);
        addFiliereBtn = findViewById(R.id.addFiliereBtn);
        addFiliereBtn.setOnClickListener(view -> {
            addFiliere();
        });
    }

    void addFiliere(){
        Filiere filiere = new Filiere();
        filiere.setNom_filiere(nomFiliereInput.getText().toString().trim());

        Log.d("CREATE","============="+ nomFiliereInput.getText().toString().trim() +"============");

        if(!filiere.getNom_filiere().isEmpty()){
            int inserted = filiereRepo.insert(filiere);

            if (inserted == -1){
                Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
            }else{
                if(modulesId.size()>0){
                    filiereRepo.associateModules(String.valueOf(inserted), modulesId);
                }
                Toast.makeText(this, "Filière Créée", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(AddFiliereActivity.this, FilieresActivity.class);
            startActivity(intent);
        }
    }
}
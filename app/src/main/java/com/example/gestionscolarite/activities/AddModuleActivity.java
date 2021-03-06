package com.example.gestionscolarite.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.controllers.FiliereRepo;
import com.example.gestionscolarite.controllers.ModuleRepo;
import com.example.gestionscolarite.models.Filiere;
import com.example.gestionscolarite.models.Module;

import java.util.ArrayList;
import java.util.List;

public class AddModuleActivity extends AppCompatActivity {

    ModuleRepo moduleRepo;

    EditText nomModuleInput;
    Button addModuleBtn;

    TextView filieresTv;
    FiliereRepo filiereRepo;
    boolean[] selectedFilieres;
    ArrayList<Integer> filieresPositions = new ArrayList<>();
    ArrayList<Integer> filieresId = new ArrayList<>();
    ArrayList<String> filieresN;
    CharSequence[] filieresNames;
    ArrayList<Filiere> filieres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        moduleRepo = new ModuleRepo(AddModuleActivity.this);
        filiereRepo = new FiliereRepo(AddModuleActivity.this);

        filieresTv = findViewById(R.id.filieresTv);
        filieres = filiereRepo.getAll();
        filieresN = filiereRepo.getAllNames();
        selectedFilieres = new boolean[filieresN.size()];
        filieresNames = filieresN.toArray(new CharSequence[filieresN.size()]);

        filieresTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddModuleActivity.this);
                builder.setTitle("Associer module aux filieres");

                builder.setMultiChoiceItems(filieresNames, selectedFilieres, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            filieresPositions.add(position);
                            filieresId.add(filieres.get(position).getId_filiere());
                        }else{
                            filieresPositions.remove(Integer.valueOf(position));
                            filieresId.remove(Integer.valueOf(filieres.get(position).getId_filiere()));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int j = 0; j < filieresPositions.size(); j++){
                            stringBuilder.append(filieres.get(filieresPositions.get(j)).getNom_filiere());
                            if (j != filieresPositions.size()-1){
                                stringBuilder.append(", ");
                            }
                        }
                        filieresTv.setText(stringBuilder.toString());
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
                        for(int j =0; j<selectedFilieres.length; j++) {
                            selectedFilieres[j] = false;
                            filieresPositions.clear();
                            filieresId.clear();
                        }

                        filieresTv.setText("");
                    }
                });

                builder.show();
            }
        });


        nomModuleInput = findViewById(R.id.nomModuleInput2);
        addModuleBtn = findViewById(R.id.createModuleBtn);
        addModuleBtn.setOnClickListener(view -> {
            addModule();
        });

    }

    void addModule(){
        Module module = new Module();
        module.setNom_module(nomModuleInput.getText().toString().trim());

        Log.d("CREATE","============="+ module +"============");

        int inserted = moduleRepo.insert(module);

        if (inserted == -1){
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            if(filieresN.size()>0){
                moduleRepo.associateToFiliere(String.valueOf(inserted), filieresId);
            }
            Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(AddModuleActivity.this, ModulesActivity.class);
        startActivity(intent);
    }

    private List<String> filieresNames(List<Filiere> filieres){
        List<String> filieresNames = new ArrayList<>();

        for(int i=0; i<filieres.size(); i++){
            filieresNames.add(filieres.get(i).getNom_filiere());
        }

        Log.d("Filieres", "filieresNames: " + filieresNames.toString());
        return filieresNames;
    }
}
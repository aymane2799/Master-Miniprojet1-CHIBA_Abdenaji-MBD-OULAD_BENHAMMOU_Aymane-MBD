package com.example.gestionscolarite.activities;

import android.content.DialogInterface;
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

import java.util.ArrayList;

public class UpdateModuleActivity extends AppCompatActivity {

    ModuleRepo moduleRepo;

    EditText nomModuleInput2;
    Button updateModuleBtn;

    String id, nom;

    TextView filieresTv2;
    FiliereRepo filiereRepo;
    boolean[] selectedFilieres;
    ArrayList<Integer> filieresPositions = new ArrayList<>();
    ArrayList<Integer> filieresId = new ArrayList<>();
    ArrayList<String> filieresN;
    CharSequence[] filieresNames;
    ArrayList<Filiere> filieres, moduleFilieres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_module);

        moduleRepo = new ModuleRepo(this);
        filiereRepo = new FiliereRepo(this);

        filieresTv2 = findViewById(R.id.filieresTv2);
        filieres = filiereRepo.getAll();
        filieresN = filiereRepo.getAllNames();
        selectedFilieres = new boolean[filieresN.size()];

        filieresNames = filieresN.toArray(new CharSequence[filieresN.size()]);
        nomModuleInput2 = findViewById(R.id.nomModuleInput2);

        getAndSetIntentData();

        moduleFilieres = moduleRepo.filieres(Integer.valueOf(id));

        for (int i=0; i<filieres.size(); i++){
            if(moduleFilieres.contains(filieres.get(i))){
                selectedFilieres[i] = true;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i<moduleFilieres.size(); i++){
            stringBuilder.append(moduleFilieres.get(i).getNom_filiere());
            if(i != moduleFilieres.size()-1 ){
                stringBuilder.append(", ");
            }
        }

        filieresTv2.setText(stringBuilder.toString());

        filieresTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateModuleActivity.this);
                builder.setTitle("Associer module aux filieres : " + nom);

//                builder.setCancelable(false);

                builder.setMultiChoiceItems(filieresNames, selectedFilieres, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            filieresPositions.add(position);
                            filieresId.add(filieres.get(position).getId_filiere());
                        }else {
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
                        filieresTv2.setText(stringBuilder.toString());
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
                        filieresTv2.setText("");
                    }
                });


            }
        });

        updateModuleBtn = findViewById(R.id.updateModuleBtn);
        updateModuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateModule();
            }
        });
    }

    void updateModule(){
        String nomFiliere = nomModuleInput2.getText().toString().trim();

        long updated = moduleRepo.updateModule(id, nomFiliere);
        if (updated == -1){
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            moduleRepo.associateToFiliere(id, filieresId);
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("idModule") && getIntent().hasExtra("nomModule")){
            // Getting Data
            id = getIntent().getStringExtra("idModule");
            nom = getIntent().getStringExtra("nomModule");

            //Setting Data
            nomModuleInput2.setText(nom);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
}
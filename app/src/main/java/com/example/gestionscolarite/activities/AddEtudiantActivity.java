package com.example.gestionscolarite.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.controllers.EtudiantRepo;
import com.example.gestionscolarite.controllers.FiliereRepo;
import com.example.gestionscolarite.models.Etudiant;
import com.example.gestionscolarite.models.Filiere;

import java.util.ArrayList;

public class AddEtudiantActivity extends AppCompatActivity {

    EtudiantRepo etudiantRepo;

    EditText cneEtudiantInput, nomEtudiantInput, prenomEtudiantInput, niveauEtudiantInput;
    Button addEtudiantBtn;

    FiliereRepo filiereRepo;
    TextView filiereTv;

    ArrayList<String> filieresN;
    CharSequence[] filieresNames;
    ArrayList<Filiere> filieres;

    int selectedItem = -1;
    Filiere selectedFiliere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        etudiantRepo = new EtudiantRepo(this);
        filiereRepo = new FiliereRepo(this);

        cneEtudiantInput = findViewById(R.id.cneEtudiantInput);
        nomEtudiantInput = findViewById(R.id.nomEtudiantInput);
        prenomEtudiantInput = findViewById(R.id.prenomEtudiantInput);
        niveauEtudiantInput = findViewById(R.id.niveauEtudiantInput);

        filieres = filiereRepo.getAll();
        filieresN = filiereRepo.getAllNames();
        filieresNames = filieresN.toArray(new CharSequence[filieresN.size()]);

        filiereTv = findViewById(R.id.filiereTv);
        filiereTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEtudiantActivity.this);
                builder.setTitle("Affecter filiere à un Etudiant");

                builder.setSingleChoiceItems(filieresNames, selectedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        selectedItem = position;
                        selectedFiliere = filieres.get(position);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filiereTv.setText(selectedFiliere.getNom_filiere());
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("Vider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedFiliere = null;
                        selectedItem = -1;
                        filiereTv.setText("");
                    }
                });

                builder.show();
            }
        });

        addEtudiantBtn = findViewById(R.id.addEtudiantBtn);
        addEtudiantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEtudiant();
            }
        });

    }

    void addEtudiant(){
        Etudiant etudiant = new Etudiant();
        etudiant.setCne(cneEtudiantInput.getText().toString().trim());
        etudiant.setNom(nomEtudiantInput.getText().toString().trim());
        etudiant.setPrenom(prenomEtudiantInput.getText().toString().trim());
        etudiant.setNiveau(niveauEtudiantInput.getText().toString().trim());

        int inserted = etudiantRepo.insert(etudiant);

        if (inserted == -1){
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            if(selectedFiliere != null){
                etudiantRepo.associateToFiliere(String.valueOf(inserted), String.valueOf(selectedFiliere.getId_filiere()));
            }
            Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(AddEtudiantActivity.this, EtudiantsActivity.class);
        startActivity(intent);
    }
}
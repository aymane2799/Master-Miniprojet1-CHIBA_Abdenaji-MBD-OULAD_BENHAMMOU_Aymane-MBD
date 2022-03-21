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
import com.example.gestionscolarite.controllers.EtudiantRepo;
import com.example.gestionscolarite.controllers.FiliereRepo;
import com.example.gestionscolarite.models.Etudiant;
import com.example.gestionscolarite.models.Filiere;

import java.util.ArrayList;

public class UpdateEtudiantActivity extends AppCompatActivity {

    EtudiantRepo etudiantRepo;

    EditText cneEtudiantInput, nomEtudiantInput, prenomEtudiantInput, niveauEtudiantInput;
    Button updateEtudiantBtn;

    FiliereRepo filiereRepo;
    TextView filiereTv;

    String id, cne, nom, prenom, niveau;
    int selectedItem = -1;
    Filiere selectedFiliere, etudiantFiliere;

    ArrayList<String> filieresN;
    CharSequence[] filieresNames;
    ArrayList<Filiere> filieres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_etudiant);

        etudiantRepo = new EtudiantRepo(this);
        filiereRepo = new FiliereRepo(this);

        cneEtudiantInput = findViewById(R.id.cneEtudiantInput2);
        nomEtudiantInput = findViewById(R.id.nomEtudiantInput2);
        prenomEtudiantInput = findViewById(R.id.prenomEtudiantInput2);
        niveauEtudiantInput = findViewById(R.id.niveauEtudiantInput2);
        filiereTv = findViewById(R.id.filiereTv2);

        filieres = filiereRepo.getAll();
        filieresN = filiereRepo.getAllNames();
        filieresNames = filieresN.toArray(new CharSequence[filieresN.size()]);

        getAndSetIntentData();

        etudiantFiliere = etudiantRepo.filiere(Integer.valueOf(id));
        if(etudiantFiliere != null){
            selectedFiliere = etudiantFiliere;
            for (int i = 0; i<filieres.size(); i++){
                if (etudiantFiliere == filieres.get(i)){
                    selectedItem = i;
                }
            }
            filiereTv.setText(etudiantFiliere.getNom_filiere());
        }

        filiereTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateEtudiantActivity.this);
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

        updateEtudiantBtn = findViewById(R.id.updateEtudiantBtn);
        updateEtudiantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEtudiant();
            }
        });
    }

    void updateEtudiant(){
        Etudiant etudiant = new Etudiant();
        etudiant.setCne(cneEtudiantInput.getText().toString().trim());
        etudiant.setNom(nomEtudiantInput.getText().toString().trim());
        etudiant.setPrenom(prenomEtudiantInput.getText().toString().trim());
        etudiant.setNiveau(niveauEtudiantInput.getText().toString().trim());

        long updated = etudiantRepo.updateEtudiant(id, etudiant);
        if (updated == -1){
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            etudiantRepo.associateToFiliere(id, String.valueOf(selectedFiliere.getId_filiere()));
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("idEtudiant") && getIntent().hasExtra("cneEtudiant") && getIntent().hasExtra("nomEtudiant") && getIntent().hasExtra("prenomEtudiant") && getIntent().hasExtra("niveauEtudiant")){
            // Getting Data
            id = getIntent().getStringExtra("idEtudiant");
            cne = getIntent().getStringExtra("cneEtudiant");
            nom = getIntent().getStringExtra("nomEtudiant");
            prenom = getIntent().getStringExtra("prenomEtudiant");
            niveau = getIntent().getStringExtra("niveauEtudiant");

            //Setting Data
            cneEtudiantInput.setText(cne);
            nomEtudiantInput.setText(nom);
            prenomEtudiantInput.setText(prenom);
            niveauEtudiantInput.setText(niveau);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
}
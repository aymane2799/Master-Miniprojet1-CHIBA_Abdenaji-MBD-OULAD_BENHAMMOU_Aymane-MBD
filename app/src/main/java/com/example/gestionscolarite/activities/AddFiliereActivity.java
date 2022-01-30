package com.example.gestionscolarite.activities;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gestionscolarite.R;
import com.example.gestionscolarite.controllers.FiliereRepo;
import com.example.gestionscolarite.models.Filiere;
import com.google.android.material.textfield.TextInputEditText;

public class AddFiliereActivity extends AppCompatActivity {

    FiliereRepo filiereRepo;

    EditText nomFiliereInput;
    Button addFiliereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filiere);

        filiereRepo = new FiliereRepo(AddFiliereActivity.this);

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

        int inserted = filiereRepo.insert(filiere);

        if (inserted == -1){
            Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Filière Créée", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(AddFiliereActivity.this, FilieresActivity.class);
        startActivity(intent);

    }
}
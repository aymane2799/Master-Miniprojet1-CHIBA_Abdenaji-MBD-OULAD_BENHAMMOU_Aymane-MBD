package com.example.gestionscolarite.activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gestionscolarite.R;

public class MainActivity extends AppCompatActivity {

    TextView filieresCountTxt;
    TextView modulesCountTxt;
    TextView etudiantsCountTxt;

    TextView filieresTxt;
    TextView modulesTxt;
    TextView etudiantsTxt;

    Button filieresDetailsBtn;
    Button modulesDetailsBtn;
    Button etudiantsDetailsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filieresCountTxt = findViewById(R.id.filieresCountTxt);
        modulesCountTxt = findViewById(R.id.modulesCountTxt);
        etudiantsCountTxt = findViewById(R.id.etudiantsCountTxt);

        filieresTxt = findViewById(R.id.modulesTxt);
        modulesTxt = findViewById(R.id.modulesTxt);
        etudiantsTxt = findViewById(R.id.etudiantsTxt);

        filieresDetailsBtn = findViewById(R.id.filieresDetailsBtn);
        modulesDetailsBtn = findViewById(R.id.modulesDetailsBtn);
        etudiantsDetailsBtn = findViewById(R.id.etudiantsDetailsBtn);

        filieresDetailsBtn.setOnClickListener(view -> {
            toFilieresDetails();
        });
        modulesDetailsBtn.setOnClickListener(view -> {
            toModulesDetails();
        });
        etudiantsDetailsBtn.setOnClickListener(view -> {
            toEtudiantsDetails();
        });

    }

    void toFilieresDetails(){
        Intent intent = new Intent(MainActivity.this, Filieres.class);
        startActivity(intent);
    }
    void toModulesDetails(){
        Intent intent = new Intent(MainActivity.this, Modules.class);
        startActivity(intent);
    }
    void toEtudiantsDetails(){
        Intent intent = new Intent(MainActivity.this, Etudiants.class);
        startActivity(intent);
    }
}
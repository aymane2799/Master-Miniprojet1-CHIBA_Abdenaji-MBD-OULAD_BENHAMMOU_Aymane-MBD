package com.example.gestionscolarite.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gestionscolarite.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FilieresActivity extends AppCompatActivity {

    FloatingActionButton filieresActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filieres);

        filieresActionButton = findViewById(R.id.filieresActionButton);
        filieresActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(FilieresActivity.this, AddFiliereActivity.class);
            startActivity(intent);
        });
    }
}
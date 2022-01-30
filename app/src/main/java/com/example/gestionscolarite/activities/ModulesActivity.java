package com.example.gestionscolarite.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gestionscolarite.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ModulesActivity extends AppCompatActivity {

    FloatingActionButton modulesActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        modulesActionButton = findViewById(R.id.modulesActionButton);
        modulesActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(ModulesActivity.this, AddModuleActivity.class);
            startActivity(intent);
        });
    }
}
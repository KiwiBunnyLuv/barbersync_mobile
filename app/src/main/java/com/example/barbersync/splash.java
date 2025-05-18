package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(() -> synchroniserAPI(), 1500); // 1.5s de splash
    }

    private void synchroniserAPI() {
       SyncManager synchro = new SyncManager();

        try {
            synchro.synchroniserDepuisApi(this);
            // Si la synchro réussit

            //finish(); // Ferme le splash
        } catch(Exception e) {
            // Sinon : erreur de connexion
            Toast.makeText(this, "Problème de connexion à l’API", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(splash.this, DashBoard.class);
        startActivity(intent);
    }
}
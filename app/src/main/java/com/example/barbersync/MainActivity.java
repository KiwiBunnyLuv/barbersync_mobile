package com.example.barbersync;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.textResult);

        // Autoriser les requêtes réseau sur le thread principal (à éviter en production)
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        // Appeler l'API Flask
        String result = Api.get("http://10.0.2.2:5000/nouveautes"); //ou api de ton ordi

        if (result != null) {
            textResult.setText(result);
        } else {
            textResult.setText("Erreur de connexion à l’API.");
        }
    }
}
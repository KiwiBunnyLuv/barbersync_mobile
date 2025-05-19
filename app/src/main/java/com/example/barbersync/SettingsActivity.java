package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        TextView nameMessage = findViewById(R.id.welcomeMessage);

        findViewById(R.id.nav_home).setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, DashBoard.class);
            startActivity(intent);
        });
        Button modifierClientButton = findViewById(R.id.btnModifierClient);
        modifierClientButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, ModifyClientActivity.class);
            startActivity(intent);
        });
        Button rendezVousButton = findViewById(R.id.btnRendezVous);
        rendezVousButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, HistoriqueRendezVousActivity.class);
            startActivity(intent);
        });
        Button logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(v -> {

            if (Client.CLIENT_COURANT != null) {
                Client.CLIENT_COURANT = null;
            }
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
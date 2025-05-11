package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationCompleteActivity extends AppCompatActivity {

    private Button btnTerminer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_complete);

        btnTerminer = findViewById(R.id.btnTerminer);

        btnTerminer.setOnClickListener(v -> {
            // Rediriger vers le tableau de bord
            Intent intent = new Intent(this, DashBoard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
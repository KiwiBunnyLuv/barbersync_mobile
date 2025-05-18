package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NouveauteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveaute_detail);

        TextView titre = findViewById(R.id.titreDetail);
        TextView description = findViewById(R.id.descriptionDetail);
        TextView dateDebut = findViewById(R.id.dateDebutDetail);
        TextView dateFin = findViewById(R.id.dateFinDetail);
        TextView type = findViewById(R.id.typeDetail);

        Button retourBtn = (Button) findViewById(R.id.retourBtn);
        retourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NouveauteDetailActivity.this, DashBoard.class);
                startActivity(i);
            }
        });

        // Récupérer les données passées via l'intent
        titre.setText(getIntent().getStringExtra("titre"));
        description.setText(getIntent().getStringExtra("description"));
        dateDebut.setText(getIntent().getStringExtra("dateDebut"));
        dateFin.setText(getIntent().getStringExtra("dateFin"));
        type.setText(getIntent().getStringExtra("type"));
    }
}
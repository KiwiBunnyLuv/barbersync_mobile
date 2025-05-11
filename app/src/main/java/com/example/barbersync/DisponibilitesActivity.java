package com.example.barbersync;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DisponibilitesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDisponibilites;
    private TextView tvTitre;
    private ImageButton btnRetour;

    private Coiffeur coiffeur;
    private List<CreneauCoiffeur> creneauxCoiffeur = new ArrayList<>();
    private List<Creneau> creneaux = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilites);

        // Récupérer le coiffeur sélectionné
        coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");
        if (coiffeur == null) {
            Toast.makeText(this, "Erreur: coiffeur non trouvé", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initialiserVues();
        configurerBoutonRetour();

        // Définir le titre avec le nom du coiffeur
        tvTitre.setText("Disponibilités");

        // Charger les disponibilités
        chargerDisponibilites();
    }

    private void initialiserVues() {
        recyclerViewDisponibilites = findViewById(R.id.recyclerViewDisponibilites);
        tvTitre = findViewById(R.id.tvTitre);
        btnRetour = findViewById(R.id.btnRetour);

        recyclerViewDisponibilites.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configurerBoutonRetour() {
        btnRetour.setOnClickListener(v -> finish());
    }

    private void chargerDisponibilites() {
        // Afficher un indicateur de chargement
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        new Thread(() -> {
            Api api = new Api();

            // Charger tous les créneaux
            creneaux = api.getCreneau();

            // Charger tous les créneaux coiffeur
            creneauxCoiffeur = api.getCreneauCoiffeur();

            runOnUiThread(() -> {
                if (creneaux != null && creneauxCoiffeur != null) {
                    CreneauAdapter adapter = new CreneauAdapter(this, creneauxCoiffeur, coiffeur);
                    recyclerViewDisponibilites.setAdapter(adapter);
                    findViewById(R.id.progressBar).setVisibility(View.GONE);

                    // Vérifier si des créneaux sont disponibles
                    if (adapter.getItemCount() == 0) {
                        findViewById(R.id.tvAucuneDisponibilite).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.tvAucuneDisponibilite).setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(this, "Erreur lors du chargement des disponibilités", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    findViewById(R.id.tvAucuneDisponibilite).setVisibility(View.VISIBLE);
                }
            });
        }).start();
    }

    // Getter pour que l'adapter puisse accéder aux créneaux
    public List<Creneau> getCreneaux() {
        return creneaux;
    }
}
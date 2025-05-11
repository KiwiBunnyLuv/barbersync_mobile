package com.example.barbersync;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoriqueRendezVousActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProchains, recyclerViewAnciens;
    private TextView tvTitreProchain, tvTitreAnciens;
    private ImageButton btnRetour;

    private List<RendezVous> tousLesRendezVous = new ArrayList<>();
    private List<RendezVous> prochainsRendezVous = new ArrayList<>();
    private List<RendezVous> anciensRendezVous = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_rendez_vous);

        try {
            initialiserVues();
            configurerBoutonRetour();

            // Charger les données
            chargerRendezVous();
        } catch (Exception e) {
            Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void initialiserVues() {
        recyclerViewProchains = findViewById(R.id.recyclerViewProchains);
        recyclerViewAnciens = findViewById(R.id.recyclerViewAnciens);
        tvTitreProchain = findViewById(R.id.tvTitreProchain);
        tvTitreAnciens = findViewById(R.id.tvTitreAnciens);
        btnRetour = findViewById(R.id.btnRetour);

        // Assurez-vous que les RecyclerView ont un LayoutManager
        recyclerViewProchains.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnciens.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configurerBoutonRetour() {
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }
    }

    private void chargerRendezVous() {
        // Afficher un indicateur de chargement
        View progressBar = findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        // S'assurer que CLIENT_COURANT n'est pas null
        if (Client.CLIENT_COURANT == null) {
            Toast.makeText(this, "Erreur: Vous n'êtes pas connecté", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        new Thread(() -> {
            try {
                Api api = new Api();
                tousLesRendezVous = api.getRendezVous();

                if (tousLesRendezVous != null) {
                    // Trier les rendez-vous en prochains et anciens
                    trierRendezVous();

                    // Mettre à jour l'UI sur le thread principal
                    runOnUiThread(() -> {
                        mettreAJourUI();
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Aucun rendez-vous trouvé", Toast.LENGTH_SHORT).show();
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Erreur lors du chargement des rendez-vous: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                    e.printStackTrace();
                });
            }
        }).start();
    }

    private void trierRendezVous() {
        prochainsRendezVous.clear();
        anciensRendezVous.clear();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date aujourdhui = new Date();

        for (RendezVous rdv : tousLesRendezVous) {
            try {
                if (rdv.getCreneau() == null) {
                    continue; // Ignorer les rendez-vous avec un créneau null
                }

                Date dateRdv = sdf.parse(rdv.getCreneau().getDate());
                if (dateRdv != null && (dateRdv.after(aujourdhui) || dateRdv.equals(aujourdhui))) {
                    prochainsRendezVous.add(rdv);
                } else {
                    anciensRendezVous.add(rdv);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                // En cas d'erreur, mettre le rendez-vous dans les prochains par défaut
                if (rdv.getCreneau() != null) {
                    prochainsRendezVous.add(rdv);
                }
            }
        }
    }

    private void mettreAJourUI() {
        try {
            // Adapter pour les prochains rendez-vous
            if (!prochainsRendezVous.isEmpty()) {
                RendezVousAdapter adapterProchains = new RendezVousAdapter(this, prochainsRendezVous, true);
                recyclerViewProchains.setAdapter(adapterProchains);
                tvTitreProchain.setVisibility(View.VISIBLE);
                recyclerViewProchains.setVisibility(View.VISIBLE);
            } else {
                tvTitreProchain.setVisibility(View.GONE);
                recyclerViewProchains.setVisibility(View.GONE);
            }

            // Adapter pour les anciens rendez-vous
            if (!anciensRendezVous.isEmpty()) {
                RendezVousAdapter adapterAnciens = new RendezVousAdapter(this, anciensRendezVous, false);
                recyclerViewAnciens.setAdapter(adapterAnciens);
                tvTitreAnciens.setVisibility(View.VISIBLE);
                recyclerViewAnciens.setVisibility(View.VISIBLE);
            } else {
                tvTitreAnciens.setVisibility(View.GONE);
                recyclerViewAnciens.setVisibility(View.GONE);
            }

            // Si aucun rendez-vous n'est trouvé, afficher un message
            if (prochainsRendezVous.isEmpty() && anciensRendezVous.isEmpty()) {
                Toast.makeText(this, "Vous n'avez pas de rendez-vous", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'affichage des rendez-vous: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
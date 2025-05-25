/****************************************
 * Fichier : HistoriqueRendezVousActivity.java
 * Auteur : Ramin Amiri
 * Fonctionnalité : MOBHIST1 - Affiche l'historique des rendez-vous depuis l'API Flask
 * Date : 2025-05-25
 *
 * Vérification :
 * 2025-05-26     Samit Adelyar        Approuvé
 =========================================================
 * Historique de modifications :
 * 2025-05-26     Ramin Amiri           Correction pour compatibilité avec API Flask
 * 2025-05-30     Ramin Amiri           Correction de la logique de tri des rendez-vous
 * 2025-12-18     Ramin Amiri           Correction des expressions lambda pour compatibilité Android
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Activité qui affiche l'historique des rendez-vous de l'utilisateur depuis l'API Flask,
 * séparés entre les rendez-vous à venir et les rendez-vous passés.
 */
public class HistoriqueRendezVousActivity extends AppCompatActivity {

    private static final String TAG = "HistoriqueRDV";

    private RecyclerView recyclerViewProchains, recyclerViewAnciens;
    private TextView tvTitreProchain, tvTitreAnciens;
    private TextView tvAucunProchain, tvAucunAncien;
    private ImageButton btnRetour;
    private ProgressBar progressBar;

    private List<RendezVous> prochainsRendezVous = new ArrayList<>();
    private List<RendezVous> anciensRendezVous = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_rendez_vous);

        try {
            initialiserVues();
            configurerBoutonRetour();

            // Vérifier que le client est connecté
            if (Client.CLIENT_COURANT == null) {
                Toast.makeText(this, "Erreur: aucun client connecté", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            // Charger les rendez-vous depuis l'API Flask
            chargerRendezVousDepuisAPI();

        } catch (Exception e) {
            Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Erreur d'initialisation: " + e.getMessage());
            finish();
        }
    }

    /**
     * Initialise les références aux vues de l'activité
     */
    private void initialiserVues() {
        recyclerViewProchains = findViewById(R.id.recyclerViewProchains);
        recyclerViewAnciens = findViewById(R.id.recyclerViewAnciens);
        tvTitreProchain = findViewById(R.id.tvTitreProchain);
        tvTitreAnciens = findViewById(R.id.tvTitreAnciens);
        btnRetour = findViewById(R.id.btnRetour);
        progressBar = findViewById(R.id.progressBar);
        tvAucunProchain = findViewById(R.id.tvAucunProchain);
        tvAucunAncien = findViewById(R.id.tvAucunAncien);

        // Configuration des RecyclerView
        recyclerViewProchains.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnciens.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Configure le bouton de retour pour terminer l'activité
     */
    private void configurerBoutonRetour() {
        if (btnRetour != null) {
            btnRetour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * Vérifie si une date est dans le passé
     */
    private boolean estDatePassee(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date dateRendezVous = sdf.parse(dateStr);

            // Obtenir la date d'aujourd'hui sans l'heure
            Calendar ajd = Calendar.getInstance();
            ajd.set(Calendar.HOUR_OF_DAY, 0);
            ajd.set(Calendar.MINUTE, 0);
            ajd.set(Calendar.SECOND, 0);
            ajd.set(Calendar.MILLISECOND, 0);

            return dateRendezVous.before(ajd.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "Erreur lors du parsing de la date: " + dateStr);
            return false;
        }
    }

    /**
     * Charge les rendez-vous depuis l'API Flask
     */
    private void chargerRendezVousDepuisAPI() {
        // Afficher le spinner de chargement
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RendezVous> rendezVousList = new ArrayList<>();

                try {
                    // Utiliser la méthode de l'API pour récupérer les rendez-vous
                    Api api = new Api();
                    rendezVousList = api.getRendezVous();
                } catch (Exception e) {
                    Log.e(TAG, "Erreur lors de la récupération des rendez-vous: " + e.getMessage());
                }

                final List<RendezVous> finalRendezVousList = rendezVousList;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        separerRendezVous(finalRendezVousList);
                        mettreAJourUI();

                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * Sépare les rendez-vous entre à venir et passés basé sur la date
     */
    private void separerRendezVous(List<RendezVous> tousLesRendezVous) {
        prochainsRendezVous.clear();
        anciensRendezVous.clear();

        for (RendezVous rdv : tousLesRendezVous) {
            // Vérifier si la date du rendez-vous est passée
            if (rdv.getCreneau() != null && estDatePassee(rdv.getCreneau().getDate())) {
                // Rendez-vous passé (basé sur la date)
                anciensRendezVous.add(rdv);
            } else {
                // Rendez-vous à venir
                prochainsRendezVous.add(rdv);
            }
        }
    }

    /**
     * Met à jour l'interface utilisateur avec les rendez-vous triés
     */
    private void mettreAJourUI() {
        try {
            // Adapter pour les prochains rendez-vous
            if (!prochainsRendezVous.isEmpty()) {
                RendezVousAdapter adapterProchains = new RendezVousAdapter(this, prochainsRendezVous, true);
                recyclerViewProchains.setAdapter(adapterProchains);
                tvTitreProchain.setVisibility(View.VISIBLE);
                recyclerViewProchains.setVisibility(View.VISIBLE);
                if (tvAucunProchain != null) tvAucunProchain.setVisibility(View.GONE);
            } else {
                tvTitreProchain.setVisibility(View.VISIBLE);
                recyclerViewProchains.setVisibility(View.GONE);
                if (tvAucunProchain != null) {
                    tvAucunProchain.setVisibility(View.VISIBLE);
                }
            }

            // Adapter pour les anciens rendez-vous
            if (!anciensRendezVous.isEmpty()) {
                RendezVousAdapter adapterAnciens = new RendezVousAdapter(this, anciensRendezVous, false);
                recyclerViewAnciens.setAdapter(adapterAnciens);
                tvTitreAnciens.setVisibility(View.VISIBLE);
                recyclerViewAnciens.setVisibility(View.VISIBLE);
                if (tvAucunAncien != null) tvAucunAncien.setVisibility(View.GONE);
            } else {
                tvTitreAnciens.setVisibility(View.VISIBLE);
                recyclerViewAnciens.setVisibility(View.GONE);
                if (tvAucunAncien != null) {
                    tvAucunAncien.setVisibility(View.VISIBLE);
                }
            }

            // Si aucun rendez-vous n'est trouvé, afficher un message
            if (prochainsRendezVous.isEmpty() && anciensRendezVous.isEmpty()) {
                Toast.makeText(this, "Vous n'avez pas de rendez-vous", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'affichage des rendez-vous", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Erreur lors de la mise à jour de l'UI: " + e.getMessage());
        }
    }
}
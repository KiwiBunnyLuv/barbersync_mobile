/****************************************
 * Fichier : AvisCoiffeurActivity.java
 * Auteur : Ramin Amiri
 * Fonctionnalité : MOBAVIS2 - Affiche la liste des avis pour un coiffeur spécifique
 * Date : 2025-05-30
 *
 * Vérification :
 * 2025-05-30     Samit Adelyar        Approuvé
 =========================================================
 * Historique de modifications :
 * 2025-05-30     Ramin Amiri           Ajout de la récupération des avis depuis l'API Flask
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

import java.util.ArrayList;
import java.util.List;

/**
 * Activité qui affiche tous les avis pour un coiffeur spécifique.
 * Les avis sont récupérés depuis l'API Flask.
 */
public class AvisCoiffeurActivity extends AppCompatActivity {

    private static final String TAG = "AvisCoiffeurActivity";

    private RecyclerView recyclerViewAvis;
    private ImageButton btnRetour;
    private TextView tvTitreCoiffeur;
    private ProgressBar progressBar;
    private TextView tvAucunAvis;

    private Coiffeur coiffeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis_coiffeur);

        // Récupération du coiffeur depuis l'intent
        coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");
        if (coiffeur == null) {
            Toast.makeText(this, "Erreur: coiffeur non trouvé", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialisation des vues
        initialiserVues();

        // Configuration du bouton retour
        btnRetour.setOnClickListener(v -> finish());

        // Chargement des avis depuis l'API
        chargerAvisDepuisAPI();
    }

    /**
     * Initialise les références aux vues de l'activité
     */
    private void initialiserVues() {
        recyclerViewAvis = findViewById(R.id.recyclerViewAvis);
        btnRetour = findViewById(R.id.btnRetour);
        tvTitreCoiffeur = findViewById(R.id.tvTitreCoiffeur);
        progressBar = findViewById(R.id.progressBar);
        tvAucunAvis = findViewById(R.id.tvAucunAvis);

        // Configurer le RecyclerView
        recyclerViewAvis.setLayoutManager(new LinearLayoutManager(this));

        // Définir le titre avec le nom du coiffeur
        tvTitreCoiffeur.setText("Avis sur " + coiffeur.getName());

        // Masquer le message "aucun avis" par défaut
        if (tvAucunAvis != null) {
            tvAucunAvis.setVisibility(View.GONE);
        }
    }

    /**
     * Charge les avis depuis l'API Flask
     */
    private void chargerAvisDepuisAPI() {
        // Afficher le spinner de chargement
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        // Masquer le RecyclerView pendant le chargement
        recyclerViewAvis.setVisibility(View.GONE);

        new Thread(() -> {
            List<Avis> listeAvis = new ArrayList<>();

            try {
                // Utiliser la méthode de l'API pour récupérer les avis du coiffeur
                Api api = new Api();
                listeAvis = api.getAvisParCoiffeur(coiffeur.getId());

                Log.d(TAG, "Avis récupérés pour " + coiffeur.getName());
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de la récupération des avis: " + e.getMessage());
            }

            // Liste finale pour le runOnUiThread
            final List<Avis> avisFinaux = listeAvis;

            // Mettre à jour l'interface sur le thread principal
            runOnUiThread(() -> {
                // Masquer le spinner
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }

                // Afficher les avis ou un message si aucun avis
                if (!avisFinaux.isEmpty()) {
                    AvisAdapter adapter = new AvisAdapter(this, avisFinaux);
                    recyclerViewAvis.setAdapter(adapter);
                    recyclerViewAvis.setVisibility(View.VISIBLE);

                    if (tvAucunAvis != null) {
                        tvAucunAvis.setVisibility(View.GONE);
                    }
                } else {
                    recyclerViewAvis.setVisibility(View.GONE);

                    if (tvAucunAvis != null) {
                        tvAucunAvis.setVisibility(View.VISIBLE);
                        tvAucunAvis.setText("Aucun avis pour " + coiffeur.getName() + " pour le moment.\n\nSoyez le premier à laisser votre avis!");
                    }
                }
            });
        }).start();
    }
}
/****************************************
 Fichier : AjoutRendezVousActivity.java
 Auteur : Ramin Amiri
 Fonctionnalité : MOBDISPONIBILITE - Permet d'afficher la page de choix des disponibilités pour l'ajout d'un rendez-vous
 Date : 2025-05-21

 Vérification :
 2025-05-22     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-22     Ramin Amiri           Modification pour afficher toutes les disponibilités sur une seule page
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activité qui permet de choisir une disponibilité pour prendre un rendez-vous
 * avec un coiffeur.
 */
public class AjoutRendezVousActivity extends AppCompatActivity {

    private Coiffeur coiffeur;
    private LinearLayout containerLayout;
    private Map<String, List<String>> disponibilites = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_rendez_vous);

        // Récupérer le coiffeur sélectionné
        coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");
        if (coiffeur == null) {
            Toast.makeText(this, "Erreur: coiffeur non trouvé", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialisation des vues
        TextView tvTitre = findViewById(R.id.tvTitre);
        containerLayout = findViewById(R.id.containerLayout);
        ImageButton btnRetour = findViewById(R.id.btnRetour);

        tvTitre.setText("disponibilités");

        // Configuration du bouton retour
        btnRetour.setOnClickListener(v -> finish());

        // Chargement des données hardcodées pour la démo
        chargerDisponibilitesDemo();

        // Afficher les disponibilités par jour
        afficherDisponibilites();
    }

    /**
     * Charge des données de disponibilités hardcodées pour la démo
     */
    private void chargerDisponibilitesDemo() {
        // Jour 1
        List<String> heuresLundi = new ArrayList<>();
        heuresLundi.add("09:00");
        heuresLundi.add("14:00");
        heuresLundi.add("15:30");
        disponibilites.put("Lundi 7 mars", heuresLundi);

        // Jour 2
        List<String> heuresMercredi = new ArrayList<>();
        heuresMercredi.add("08:00");
        heuresMercredi.add("08:30");
        heuresMercredi.add("15:30");
        heuresMercredi.add("16:30");
        disponibilites.put("Mercredi 9 mars", heuresMercredi);
    }

    /**
     * Affiche toutes les disponibilités sur l'écran, groupées par jour
     */
    private void afficherDisponibilites() {
        containerLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        // Pour chaque jour
        for (Map.Entry<String, List<String>> entry : disponibilites.entrySet()) {
            String jour = entry.getKey();
            List<String> heures = entry.getValue();

            // Ajouter le jour
            TextView tvJour = new TextView(this);
            tvJour.setText(jour);
            tvJour.setTextColor(getResources().getColor(android.R.color.white));
            tvJour.setTextSize(18);

            // Padding et marges
            LinearLayout.LayoutParams jourParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            jourParams.setMargins(0, 24, 0, 16);
            tvJour.setLayoutParams(jourParams);

            containerLayout.addView(tvJour);

            // Ajouter une ligne horizontale
            View divider = new View(this);
            divider.setBackgroundColor(getResources().getColor(android.R.color.white));
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1
            );
            dividerParams.setMargins(0, 0, 0, 16);
            divider.setLayoutParams(dividerParams);
            containerLayout.addView(divider);

            // Pour chaque heure disponible ce jour
            for (String heure : heures) {
                // Créer un bouton personnalisé pour cette heure
                View heureView = inflater.inflate(R.layout.item_heure_disponible, containerLayout, false);
                TextView tvHeure = heureView.findViewById(R.id.tvHeure);
                tvHeure.setText(heure);

                // Ajouter un listener sur le bouton
                heureView.setOnClickListener(v -> {
                    // Extraire la date
                    String date = extraireDate(jour);

                    // Rediriger vers la page de confirmation
                    Intent intent = new Intent(AjoutRendezVousActivity.this, ConfirmationRendezVousActivity.class);
                    intent.putExtra("coiffeur", coiffeur);
                    intent.putExtra("date", date);
                    intent.putExtra("heure", heure);
                    startActivity(intent);
                });

                containerLayout.addView(heureView);
            }
        }
    }

    /**
     * Extrait une date au format yyyy-MM-dd à partir du texte affiché
     *
     * @param jourAffiche le jour affiché (ex: "Lundi 7 mars")
     * @return la date au format yyyy-MM-dd
     */
    private String extraireDate(String jourAffiche) {
        // Pour la démo, on utilise une date hardcodée
        if (jourAffiche.contains("7 mars")) {
            return "2025-03-07";
        } else if (jourAffiche.contains("9 mars")) {
            return "2025-03-09";
        }

        // Par défaut
        return "2025-04-03";
    }
}
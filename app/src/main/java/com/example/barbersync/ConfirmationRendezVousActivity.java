/****************************************
 Fichier : ConfirmationRendezVousActivity.java
 Auteur : Ramin Amiri
 Fonctionnalité : MOBCONFIRM - Permet de confirmer les détails d'un rendez-vous avant de le réserver
 Date : 2025-05-21

 Vérification :
 2025-05-22     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-22     Ramin Amiri           Ajout de valeurs hardcodées pour la démo
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Activité qui permet de confirmer les détails d'un rendez-vous avant de le réserver
 */
public class ConfirmationRendezVousActivity extends AppCompatActivity {

    private ImageButton btnRetour;
    private Button btnReserver;
    private Spinner spinnerService;
    private TextView tvPrix, tvDate, tvHeure, tvCoiffeur, tvLieu;

    private Coiffeur coiffeur;
    private String dateRendezVous;
    private String heureRendezVous;
    private Map<String, Double> servicesPrix = new HashMap<>();
    private String serviceSelectionne;
    private double prixSelectionne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_rendez_vous);

        // Récupérer les données passées
        coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");
        dateRendezVous = getIntent().getStringExtra("date");
        heureRendezVous = getIntent().getStringExtra("heure");

        // Si les données sont nulles, utiliser des valeurs par défaut pour la démo
        if (coiffeur == null) {
            coiffeur = new Coiffeur(1, "Jack Nack", "Expert en coupes modernes.");
        }

        if (dateRendezVous == null) {
            dateRendezVous = "2025-04-03";
        }

        if (heureRendezVous == null) {
            heureRendezVous = "09:30";
        }

        initialiserVues();
        configurerBoutonRetour();

        // Charger les services du coiffeur
        chargerServicesCoiffeur();
    }

    /**
     * Initialise les vues de l'activité
     */
    private void initialiserVues() {
        btnRetour = findViewById(R.id.btnRetour);
        btnReserver = findViewById(R.id.btnReserver);
        spinnerService = findViewById(R.id.spinnerService);
        tvPrix = findViewById(R.id.tvPrix);
        tvDate = findViewById(R.id.tvDate);
        tvHeure = findViewById(R.id.tvHeure);
        tvCoiffeur = findViewById(R.id.tvCoiffeur);
        tvLieu = findViewById(R.id.tvLieu);

        // Définir les informations fixes
        tvCoiffeur.setText(coiffeur.getName());
        tvLieu.setText("salon Bald Barbers"); // Hardcoded pour simplifier

        // Formater la date pour l'affichage
        tvDate.setText(dateRendezVous);
        tvHeure.setText(heureRendezVous);

        // Configurer le bouton de réservation
        btnReserver.setOnClickListener(v -> confirmerReservation());
    }

    /**
     * Configure le bouton de retour
     */
    private void configurerBoutonRetour() {
        btnRetour.setOnClickListener(v -> finish());
    }

    /**
     * Charge les services du coiffeur (données hardcodées pour démo)
     */
    private void chargerServicesCoiffeur() {
        // Données hardcodées pour la démo
        servicesPrix.put("coupe régulière", 15.00);
        servicesPrix.put("coupe + barbe", 25.00);
        servicesPrix.put("coupe enfant", 10.00);
        servicesPrix.put("rasage", 12.00);

        // Configurer le spinner
        configurerSpinnerServices();
    }

    /**
     * Configure le spinner des services
     */
    private void configurerSpinnerServices() {
        // Créer la liste des services pour le spinner
        final List<String> listeServices = new ArrayList<>(servicesPrix.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listeServices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerService.setAdapter(adapter);

        // Définir le listener pour mettre à jour le prix
        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceSelectionne = listeServices.get(position);
                prixSelectionne = servicesPrix.get(serviceSelectionne);
                tvPrix.setText(String.format(Locale.getDefault(), "%.2f$", prixSelectionne));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire
            }
        });

        // Sélectionner le premier service par défaut
        if (!listeServices.isEmpty()) {
            serviceSelectionne = listeServices.get(0);
            prixSelectionne = servicesPrix.get(serviceSelectionne);
            tvPrix.setText(String.format(Locale.getDefault(), "%.2f$", prixSelectionne));
        }
    }

    /**
     * Confirme la réservation et passe à l'écran suivant
     */
    private void confirmerReservation() {
        // Pour la démo, on passe directement à l'écran de confirmation
        Intent intent = new Intent(this, ReservationCompleteActivity.class);
        startActivity(intent);
        finish(); // Fermer cette activité
    }
}
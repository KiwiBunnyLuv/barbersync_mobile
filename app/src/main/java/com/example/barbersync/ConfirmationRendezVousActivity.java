/****************************************
 * Fichier : ConfirmationRendezVousActivity.java
 * Auteur : Ramin Amiri
 * Fonctionnalité : MOBCONFIRM - Confirmation des détails d'un rendez-vous avant envoi à l'API
 * Date : 2025-05-28
 *
 * Vérification :
 * 2025-05-28     Samit Adelyar        Approuvé
 =========================================================
 * Historique de modifications :
 * 2025-05-24     Ramin Amiri           Version initiale
 * 2025-05-28     Ramin Amiri           Amélioration de la gestion des erreurs et affichage
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Activité qui permet de confirmer les détails d'un rendez-vous avant
 * de l'envoyer à l'API pour enregistrement
 */
public class ConfirmationRendezVousActivity extends AppCompatActivity {

    private static final String TAG = "ConfirmRDV";
    private ImageButton btnRetour;
    private Button btnReserver;
    private Spinner spinnerService;
    private TextView tvPrix, tvDate, tvHeure, tvCoiffeur, tvLieu;
    private ProgressBar progressBar;

    private Coiffeur coiffeur;
    private Creneau creneauSelectionne;
    private Map<String, Double> servicesPrix = new HashMap<>();
    private String serviceSelectionne;
    private double prixSelectionne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_rendez_vous);

        try {
            // Récupérer les données passées
            coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");
            creneauSelectionne = (Creneau) getIntent().getSerializableExtra("creneau");

            // Vérifier que les données essentielles sont présentes
            if (coiffeur == null || creneauSelectionne == null) {
                Toast.makeText(this, "Erreur: données manquantes", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            initialiserVues();
            configurerBoutonRetour();

            // Charger les services du coiffeur
            chargerServicesCoiffeur();

        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'initialisation: " + e.getMessage());
            Toast.makeText(this, "Erreur lors de l'initialisation", Toast.LENGTH_SHORT).show();
            finish();
        }
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
        progressBar = findViewById(R.id.progressBar);

        // Définir les informations fixes
        tvCoiffeur.setText(coiffeur.getName());
        tvLieu.setText("salon Bald Barbers"); // Nom du salon par défaut

        // Formater et afficher les informations du créneau
        tvDate.setText(formaterDate(creneauSelectionne.getDate()));
        tvHeure.setText(creneauSelectionne.getHeureDebut());

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
     * Charge les services du coiffeur
     */
    private void chargerServicesCoiffeur() {
        try {
            // Utiliser les services directement depuis l'objet coiffeur
            if (coiffeur.getCoupes() != null && !coiffeur.getCoupes().isEmpty()) {
                for (CoupeCoiffeur coupeCoiffeur : coiffeur.getCoupes()) {
                    // Ne prendre que les services avec un prix > 0
                    if (coupeCoiffeur.getPrix() > 0) {
                        servicesPrix.put(coupeCoiffeur.getNom(), coupeCoiffeur.getPrix());
                    }
                }
            }

            // Si aucun service disponible, ajouter des services par défaut
            if (servicesPrix.isEmpty()) {
                servicesPrix.put("Coupe standard", 20.0);
            }

            // Configurer le spinner avec les services disponibles
            configurerSpinnerServices();

        } catch (Exception e) {
            Log.e(TAG, "Erreur lors du chargement des services: " + e.getMessage());

            // Ajouter des services par défaut en cas d'erreur
            servicesPrix.clear();
            servicesPrix.put("Coupe standard", 20.0);
            configurerSpinnerServices();
        }
    }

    /**
     * Configure le spinner des services
     */
    private void configurerSpinnerServices() {
        // Créer la liste des services pour le spinner
        final List<String> listeServices = new ArrayList<>(servicesPrix.keySet());

        if (listeServices.isEmpty()) {
            Toast.makeText(this, "Aucun service disponible pour ce coiffeur", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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
     * Confirme la réservation avec une boîte de dialogue
     */
    private void confirmerReservation() {
        // Vérifier que le client est connecté
        if (Client.CLIENT_COURANT == null) {
            Toast.makeText(this, "Erreur: aucun client connecté", Toast.LENGTH_SHORT).show();
            return;
        }

        // Désactiver le bouton pour éviter les doubles clics
        btnReserver.setEnabled(false);
        btnReserver.setText("Réservation en cours...");

        // Afficher un message de confirmation
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Confirmer la réservation")
                .setMessage("Voulez-vous confirmer cette réservation?\n\n" +
                        "Date: " + tvDate.getText() + "\n" +
                        "Heure: " + tvHeure.getText() + "\n" +
                        "Service: " + serviceSelectionne + "\n" +
                        "Prix: " + tvPrix.getText())
                .setPositiveButton("Oui", (dialog, which) -> {
                    // Envoyer la réservation
                    envoyerReservation();
                })
                .setNegativeButton("Non", (dialog, which) -> {
                    // Réactiver le bouton
                    btnReserver.setEnabled(true);
                    btnReserver.setText("Réserver");
                })
                .setCancelable(false)
                .show();
    }

    /**
     * Envoie la réservation à l'API
     */
    private void envoyerReservation() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        // Vérification du client connecté
        if (Client.CLIENT_COURANT == null) {
            Toast.makeText(this, "ERREUR: Aucun client connecté. Veuillez vous reconnecter.", Toast.LENGTH_LONG).show();
            btnReserver.setEnabled(true);
            btnReserver.setText("Réserver");
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            return;
        }

        // Envoyer la réservation à l'API en arrière-plan
        new Thread(() -> {
            boolean success = false;
            String errorMessage = "";

            try {
                Api api = new Api();

                // Créer le rendez-vous via l'API
                success = api.creerRendezVous(
                        creneauSelectionne.getId(),
                        Client.CLIENT_COURANT.getId(),
                        serviceSelectionne,
                        prixSelectionne,
                        coiffeur.getId()
                );

                if (success) {
                    errorMessage = "Réservation créée avec succès!";
                } else {
                    errorMessage = "Erreur lors de la création du rendez-vous via l'API";
                }

            } catch (Exception e) {
                Log.e(TAG, "Exception lors de la réservation: " + e.getMessage());
                success = false;
                errorMessage = "Erreur de connexion: " + e.getMessage();
            }

            final boolean finalSuccess = success;
            final String finalErrorMessage = errorMessage;

            runOnUiThread(() -> {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }

                if (finalSuccess) {
                    // Succès - rediriger vers l'écran de confirmation
                    Toast.makeText(this, finalErrorMessage, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, ReservationCompleteActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Échec - réactiver le bouton et afficher l'erreur
                    Toast.makeText(this, finalErrorMessage, Toast.LENGTH_LONG).show();
                    btnReserver.setEnabled(true);
                    btnReserver.setText("Réserver");
                }
            });
        }).start();
    }

    /**
     * Formate une date du format YYYY-MM-DD au format plus lisible
     * @param dateStr Date au format YYYY-MM-DD
     * @return Date formatée
     */
    private String formaterDate(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return dateStr; // En cas d'erreur, retourner la date d'origine
        }
    }
}
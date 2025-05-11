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

public class ConfirmationRendezVousActivity extends AppCompatActivity {

    private ImageButton btnRetour;
    private Button btnReserver;
    private Spinner spinnerService;
    private TextView tvPrix, tvDate, tvHeure, tvCoiffeur, tvLieu;

    private Coiffeur coiffeur;
    private Creneau creneau;
    private List<CoupeCoiffeur> coupes = new ArrayList<>();
    private Map<String, Double> servicesPrix = new HashMap<>();
    private String serviceSelectionne;
    private double prixSelectionne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_rendez_vous);

        // Récupérer les données passées
        coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");
        creneau = (Creneau) getIntent().getSerializableExtra("creneau");

        if (coiffeur == null || creneau == null) {
            Toast.makeText(this, "Erreur: données manquantes", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initialiserVues();
        configurerBoutonRetour();

        // Charger les services du coiffeur
        chargerServicesCoiffeur();
    }

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

        // Formater la date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat affichageFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        try {
            Date date = dateFormat.parse(creneau.getDate());
            tvDate.setText(affichageFormat.format(date));
        } catch (Exception e) {
            tvDate.setText(creneau.getDate());
        }

        tvHeure.setText(creneau.getHeureDebut());

        // Configurer le bouton de réservation
        btnReserver.setOnClickListener(v -> confirmerReservation());
    }

    private void configurerBoutonRetour() {
        btnRetour.setOnClickListener(v -> finish());
    }

    private void chargerServicesCoiffeur() {
        // Afficher un indicateur de chargement
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        new Thread(() -> {
            Api api = new Api();
            List<CoupeCoiffeur> coupesPrix = api.getCoupeCoiffeurs();
            List<Coupes> toutesCoupes = api.getCoupes();

            if (coupesPrix != null && toutesCoupes != null) {
                // Filtrer les coupes pour ce coiffeur et créer la map de prix
                for (CoupeCoiffeur cc : coupesPrix) {
                    if (cc.getCoiffeur() == coiffeur.getId() && cc.getPrix() > 0) {
                        for (Coupes c : toutesCoupes) {
                            if (c.getId() == cc.getCoupe()) {
                                servicesPrix.put(c.getNom(), cc.getPrix());
                                break;
                            }
                        }
                    }
                }

                runOnUiThread(() -> {
                    configurerSpinnerServices();
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Erreur lors du chargement des services", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                });
            }
        }).start();
    }

    private void configurerSpinnerServices() {
        // Créer la liste des services pour le spinner
        final List<String> listeServices = new ArrayList<>(servicesPrix.keySet());

        if (listeServices.isEmpty()) {
            Toast.makeText(this, "Aucun service disponible pour ce coiffeur", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listeServices);
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

    private void confirmerReservation() {
        // Vérifier que tout est bien sélectionné
        if (serviceSelectionne == null) {
            Toast.makeText(this, "Veuillez sélectionner un service", Toast.LENGTH_SHORT).show();
            return;
        }

        // Afficher un indicateur de chargement
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        btnReserver.setEnabled(false);

        new Thread(() -> {
            Api api = new Api();
            boolean success = api.creerRendezVous(
                    creneau.getId(),
                    Client.CLIENT_COURANT.getId(), // Utiliser l'ID du client connecté
                    serviceSelectionne,
                    prixSelectionne,
                    coiffeur.getId()
            );

            runOnUiThread(() -> {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                if (success) {
                    // Naviguer vers la page de confirmation
                    Intent intent = new Intent(this, ReservationCompleteActivity.class);
                    startActivity(intent);
                    finish(); // Fermer cette activité
                } else {
                    Toast.makeText(this, "Erreur lors de la réservation", Toast.LENGTH_SHORT).show();
                    btnReserver.setEnabled(true);
                }
            });
        }).start();
    }
}
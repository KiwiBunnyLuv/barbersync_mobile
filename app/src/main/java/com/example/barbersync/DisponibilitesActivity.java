/****************************************
 * Fichier : DisponibilitesActivity.java
 * Auteur : Ramin Amiri
 * Fonctionnalité : MOBDISPONIBILITE - Affiche les disponibilités d'un coiffeur depuis l'API Flask
 * Date : 2025-05-28
 *
 * Vérification :
 * 2025-05-28     Samit Adelyar        Approuvé
 =========================================================
 * Historique de modifications :
 * 2025-05-28     Ramin Amiri           Utilisation directe de l'API Flask
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Intent;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Activité qui affiche les disponibilités d'un coiffeur spécifique
 * en récupérant les données directement depuis l'API Flask
 */
public class DisponibilitesActivity extends AppCompatActivity {

    private static final String TAG = "DisponibilitesActivity";

    // Variables de l'interface
    private RecyclerView recyclerViewDisponibilites;
    private TextView tvTitre;
    private TextView tvAucuneDisponibilite;
    private ProgressBar progressBar;
    private ImageButton btnRetour;

    // Variables de données
    private Coiffeur coiffeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilites);

        try {
            // Récupérer le coiffeur depuis l'intent
            coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");

            if (coiffeur == null) {
                Toast.makeText(this, "Erreur: coiffeur non trouvé", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            Log.d(TAG, "Coiffeur sélectionné: " + coiffeur.getName() + " (ID: " + coiffeur.getId() + ")");

            // Initialiser les composants
            initialiserVues();
            configurerInterface();

            // Charger les disponibilités depuis l'API Flask
            chargerDisponibilitesDepuisAPI();

        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'initialisation: " + e.getMessage(), e);
            Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Initialise toutes les vues de l'activité
     */
    private void initialiserVues() {
        recyclerViewDisponibilites = findViewById(R.id.recyclerViewDisponibilites);
        tvTitre = findViewById(R.id.tvTitre);
        tvAucuneDisponibilite = findViewById(R.id.tvAucuneDisponibilite);
        progressBar = findViewById(R.id.progressBar);
        btnRetour = findViewById(R.id.btnRetour);

        // Configurer le RecyclerView
        recyclerViewDisponibilites.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Configure l'interface utilisateur
     */
    private void configurerInterface() {
        // Définir le titre avec le nom du coiffeur
        tvTitre.setText("Disponibilités de " + coiffeur.getName());

        // Configurer le bouton de retour
        if (btnRetour != null) {
            btnRetour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        // Masquer le message d'erreur par défaut
        if (tvAucuneDisponibilite != null) {
            tvAucuneDisponibilite.setVisibility(View.GONE);
        }
    }

    /**
     * Charge les disponibilités depuis l'API Flask UNIQUEMENT
     */
    private void chargerDisponibilitesDepuisAPI() {
        // Afficher le spinner de chargement
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        // Traitement en arrière-plan
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Creneau> creneauxDisponibles = new ArrayList<>();

                try {
                    Log.d(TAG, "=== CHARGEMENT DEPUIS API FLASK SEULEMENT ===");
                    Log.d(TAG, "Coiffeur: " + coiffeur.getName() + " (ID: " + coiffeur.getId() + ")");

                    // Étape 1: Récupérer tous les créneaux depuis Flask
                    List<Creneau> tousCreneaux = recupererCreneauxDepuisFlask();
                    Log.d(TAG, "Créneaux récupérés depuis Flask: " + tousCreneaux.size());

                    // Étape 2: Récupérer toutes les relations créneau-coiffeur depuis Flask
                    List<CreneauCoiffeur> relationsCreneauCoiffeur = recupererRelationsDepuisFlask();
                    Log.d(TAG, "Relations créneau-coiffeur récupérées: " + relationsCreneauCoiffeur.size());

                    // Étape 3: Filtrer pour ce coiffeur spécifique
                    int relationsCoiffeur = 0;
                    int disponibles = 0;
                    int reserves = 0;

                    for (CreneauCoiffeur relation : relationsCreneauCoiffeur) {
                        // Vérifier si cette relation concerne notre coiffeur
                        if (relation.getCoiffeurs() == coiffeur.getId()) {
                            relationsCoiffeur++;
                            Log.d(TAG, String.format("Relation trouvée: CreneauID=%d, Dispo=%d, Reserve=%d",
                                    relation.getCreneau(), relation.getDispo(), relation.getReserve()));

                            // Vérifier si le créneau est disponible et non réservé
                            if (relation.getDispo() == 1 && relation.getReserve() == 0) {
                                disponibles++;

                                // Trouver le créneau correspondant
                                for (Creneau creneau : tousCreneaux) {
                                    if (creneau.getId() == relation.getCreneau()) {
                                        creneauxDisponibles.add(creneau);
                                        Log.d(TAG, "Créneau disponible ajouté: " + creneau.getDate() + " " +
                                                creneau.getHeureDebut() + "-" + creneau.getHeureFin());
                                        break;
                                    }
                                }
                            } else if (relation.getReserve() == 1) {
                                reserves++;
                            }
                        }
                    }

                    Log.d(TAG, "=== RÉSULTATS POUR COIFFEUR " + coiffeur.getId() + " ===");
                    Log.d(TAG, "Relations trouvées: " + relationsCoiffeur);
                    Log.d(TAG, "Disponibles: " + disponibles);
                    Log.d(TAG, "Réservés: " + reserves);
                    Log.d(TAG, "Créneaux finaux: " + creneauxDisponibles.size());

                    // Variables finales pour le runOnUiThread
                    final List<Creneau> finalCreneaux = creneauxDisponibles;
                    final int finalRelations = relationsCoiffeur;
                    final int finalDisponibles = disponibles;
                    final int finalReserves = reserves;

                    // Mettre à jour l'interface
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mettreAJourInterface(finalCreneaux, finalRelations, finalDisponibles, finalReserves);
                        }
                    });

                } catch (Exception e) {
                    Log.e(TAG, "Erreur lors du chargement depuis Flask: " + e.getMessage(), e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            afficherErreur("Erreur de connexion à l'API");
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * Récupère tous les créneaux depuis l'API Flask
     */
    private List<Creneau> recupererCreneauxDepuisFlask() {
        List<Creneau> creneaux = new ArrayList<>();

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://192.168.2.21:5000/creneau")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonString = response.body().string();
                    Log.d(TAG, "JSON créneaux reçu: " + jsonString.substring(0, Math.min(200, jsonString.length())) + "...");

                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);

                    for (JsonElement element : jsonArray) {
                        JsonObject creneauJson = element.getAsJsonObject();

                        int id = creneauJson.has("id_creneau") ? creneauJson.get("id_creneau").getAsInt() : 0;
                        String date = creneauJson.has("date") ? creneauJson.get("date").getAsString() : "";
                        String heureDebut = creneauJson.has("heure_debut") ? creneauJson.get("heure_debut").getAsString() : "";
                        String heureFin = creneauJson.has("heure_fin") ? creneauJson.get("heure_fin").getAsString() : "";

                        // Formater l'heure (enlever les secondes si présentes)
                        heureDebut = formaterHeure(heureDebut);
                        heureFin = formaterHeure(heureFin);

                        Creneau creneau = new Creneau(id, heureDebut, heureFin, date);
                        creneaux.add(creneau);
                    }
                } else {
                    Log.e(TAG, "Erreur HTTP créneaux: " + response.code());
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau créneaux: " + e.getMessage(), e);
        }

        return creneaux;
    }

    /**
     * Récupère toutes les relations créneau-coiffeur depuis l'API Flask
     */
    private List<CreneauCoiffeur> recupererRelationsDepuisFlask() {
        List<CreneauCoiffeur> relations = new ArrayList<>();

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://192.168.2.21:5000/creneauCoiffeur")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonString = response.body().string();
                    Log.d(TAG, "JSON relations reçu: " + jsonString.substring(0, Math.min(200, jsonString.length())) + "...");

                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);

                    for (JsonElement element : jsonArray) {
                        JsonObject relationJson = element.getAsJsonObject();

                        int idCoiffeur = relationJson.has("id_coiffeur") ? relationJson.get("id_coiffeur").getAsInt() : 0;
                        int idCreneau = relationJson.has("id_creneau") ? relationJson.get("id_creneau").getAsInt() : 0;
                        int dispo = relationJson.has("dispo") ? relationJson.get("dispo").getAsInt() : 0;
                        int reserve = relationJson.has("reserve") ? relationJson.get("reserve").getAsInt() : 0;

                        CreneauCoiffeur relation = new CreneauCoiffeur(idCoiffeur, idCreneau, dispo, reserve);
                        relations.add(relation);
                    }
                } else {
                    Log.e(TAG, "Erreur HTTP relations: " + response.code());
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau relations: " + e.getMessage(), e);
        }

        return relations;
    }

    /**
     * Formate l'heure en enlevant les secondes si présentes
     */
    private String formaterHeure(String heure) {
        if (heure != null && heure.length() >= 5) {
            return heure.substring(0, 5); // Garder seulement HH:MM
        }
        return heure;
    }

    /**
     * Met à jour l'interface utilisateur
     */
    private void mettreAJourInterface(List<Creneau> creneauxDisponibles, int relations, int disponibles, int reserves) {
        // Masquer le spinner
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        if (creneauxDisponibles != null && !creneauxDisponibles.isEmpty()) {
            // Afficher les créneaux
            CreneauAdapter adapter = new CreneauAdapter(this, creneauxDisponibles, coiffeur);
            recyclerViewDisponibilites.setAdapter(adapter);
            recyclerViewDisponibilites.setVisibility(View.VISIBLE);

            if (tvAucuneDisponibilite != null) {
                tvAucuneDisponibilite.setVisibility(View.GONE);
            }

            String message = creneauxDisponibles.size() + " créneaux disponibles";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        } else {
            // Aucun créneau disponible
            recyclerViewDisponibilites.setVisibility(View.GONE);

            if (tvAucuneDisponibilite != null) {
                String message;
                if (relations == 0) {
                    message = "No time slots configured for this barber";
                } else if (reserves > 0 && disponibles == 0) {
                    message = "All time slots are booked for this barber";
                } else {
                    message = "No available time slots for this barber";
                }
                tvAucuneDisponibilite.setText(message);
                tvAucuneDisponibilite.setVisibility(View.VISIBLE);
            }

            String debugMessage = String.format("Relations: %d, Disponibles: %d, Réservés: %d", relations, disponibles, reserves);
            Toast.makeText(this, debugMessage, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Affiche un message d'erreur
     */
    private void afficherErreur(String messageErreur) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        if (tvAucuneDisponibilite != null) {
            tvAucuneDisponibilite.setText("Error: " + messageErreur);
            tvAucuneDisponibilite.setVisibility(View.VISIBLE);
        }

        recyclerViewDisponibilites.setVisibility(View.GONE);
        Toast.makeText(this, "Error: " + messageErreur, Toast.LENGTH_SHORT).show();
    }
}
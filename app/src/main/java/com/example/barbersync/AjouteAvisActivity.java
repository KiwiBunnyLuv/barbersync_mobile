/****************************************
 Fichier : AjouteAvisActivity.java
 Auteur : Ramin Amiri
 Fonctionnalité : MOBAVIS1 - Permet à l'utilisateur d'ajouter un avis après un rendez-vous
 Date : 2025-05-27

 Vérification :
 2025-05-28     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-30     Ramin Amiri           Ajout de la connexion à l'API Flask
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

/**
 * Activité permettant à l'utilisateur de rédiger et soumettre un avis
 * concernant un rendez-vous passé avec un coiffeur.
 */
public class AjouteAvisActivity extends AppCompatActivity {

    private static final String TAG = "AjouteAvisActivity";

    private TextView tvTitreCoiffeur, tvInfosRdv;
    private ImageButton btnRetour;
    private EditText etCommentaire, etTitre;
    private Button btnAnnuler, btnEnvoyer, btnJoindrePhoto;
    private ImageView[] etoiles = new ImageView[5];
    private int noteActuelle = 0;
    private RendezVous rendezVous;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajoute_avis);

        // Récupération du rendez-vous depuis l'intent
        recupererRendezVous();

        // Initialisation des vues
        initialiserVues();

        // Configuration de l'interface
        configurerInterface();

        // Mise en place des listeners
        configurerListeners();
    }

    /**
     * Récupère les informations du rendez-vous depuis l'intent
     */
    private void recupererRendezVous() {
        rendezVous = (RendezVous) getIntent().getSerializableExtra("rendezVous");

        if (rendezVous == null) {
            Toast.makeText(this, "Erreur: données du rendez-vous manquantes", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Initialise les références aux vues de l'activité
     */
    private void initialiserVues() {
        // En-tête
        btnRetour = findViewById(R.id.btnRetour);

        // Informations du rendez-vous
        tvTitreCoiffeur = findViewById(R.id.tvTitreCoiffeur);
        tvInfosRdv = findViewById(R.id.tvInfosRdv);

        // Système de notation par étoiles
        etoiles[0] = findViewById(R.id.etoile1);
        etoiles[1] = findViewById(R.id.etoile2);
        etoiles[2] = findViewById(R.id.etoile3);
        etoiles[3] = findViewById(R.id.etoile4);
        etoiles[4] = findViewById(R.id.etoile5);

        // Champ de titre et commentaire
        etTitre = findViewById(R.id.etTitre);
        etCommentaire = findViewById(R.id.etCommentaire);

        // Boutons
        btnJoindrePhoto = findViewById(R.id.btnJoindrePhoto);
        btnAnnuler = findViewById(R.id.btnAnnuler);
        btnEnvoyer = findViewById(R.id.btnEnvoyer);
    }

    /**
     * Configure l'interface utilisateur avec les informations du rendez-vous
     */
    private void configurerInterface() {
        // Définir le titre avec le nom du coiffeur
        String titre = "Comment s'est passée votre coupe avec " + rendezVous.getNomCoiffeur() + "?";
        tvTitreCoiffeur.setText(titre);

        // Définir les informations du rendez-vous
        String date = formatSimpleDate(rendezVous.getCreneau().getDate());
        String heure = rendezVous.getCreneau().getHeureDebut();
        String infosRdv = rendezVous.getTypeService() + " " + rendezVous.getPrix() + "$\n" +
                date + " · " + heure;

        tvInfosRdv.setText(infosRdv);
    }

    /**
     * Configure les listeners pour les boutons et les étoiles
     */
    private void configurerListeners() {
        // Bouton de retour
        btnRetour.setOnClickListener(v -> finish());

        // Système d'étoiles
        for (int i = 0; i < etoiles.length; i++) {
            final int index = i;
            etoiles[i].setOnClickListener(v -> {
                noteActuelle = index + 1; // +1 car index commence à 0
                updateStars();
            });
        }

        // Bouton pour joindre une photo (non implémenté pour l'instant)
        btnJoindrePhoto.setOnClickListener(v -> {
            // Vérifier si la permission caméra est accordée
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Demander la permission si pas encore accordée
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                // Lancer l'appareil photo si permission déjà accordée
                ouvrirAppareilPhoto();
            }
        });

        // Bouton Annuler
        btnAnnuler.setOnClickListener(v -> finish());

        // Bouton Envoyer
        btnEnvoyer.setOnClickListener(v -> envoyerAvis());
    }

    /**
     * Met à jour l'affichage des étoiles selon la note actuelle
     */
    private void updateStars() {
        for (int i = 0; i < etoiles.length; i++) {
            if (i < noteActuelle) {
                etoiles[i].setImageResource(R.drawable.etoile_pleine);
            } else {
                etoiles[i].setImageResource(R.drawable.etoile_vide);
            }
        }
    }

    /**
     * Envoie l'avis via l'API Flask
     */
    private void envoyerAvis() {
        String titre = etTitre.getText().toString().trim();
        String commentaire = etCommentaire.getText().toString().trim();

        // Validations
        if (noteActuelle == 0) {
            Toast.makeText(this, "Veuillez sélectionner une note", Toast.LENGTH_SHORT).show();
            return;
        }

        if (titre.isEmpty()) {
            Toast.makeText(this, "Veuillez ajouter un titre", Toast.LENGTH_SHORT).show();
            return;
        }

        if (commentaire.isEmpty()) {
            Toast.makeText(this, "Veuillez ajouter un commentaire", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérifier que le client est connecté
        if (Client.CLIENT_COURANT == null) {
            Toast.makeText(this, "Vous devez être connecté pour laisser un avis", Toast.LENGTH_SHORT).show();
            return;
        }

        // Désactiver le bouton pour éviter les doubles clics
        btnEnvoyer.setEnabled(false);
        btnEnvoyer.setText("Envoi en cours...");

        // Envoyer l'avis à l'API en arrière-plan
        new Thread(() -> {
            boolean success = false;
            String message = "";

            try {
                // Vérifications de sécurité
                int idRendezVous = rendezVous.getId_rendezvous();
                if (idRendezVous <= 0) {
                    throw new Exception("ID de rendez-vous invalide: " + idRendezVous);
                }

                // Vérifier que c'est bien le rendez-vous du client connecté
                if (rendezVous.getId_user() != Client.CLIENT_COURANT.getId()) {
                    throw new Exception("Vous ne pouvez laisser un avis que pour vos propres rendez-vous.");
                }

                // Envoi via API
                Api api = new Api();
                success = api.ajouterAvis(idRendezVous, titre, commentaire, noteActuelle, "");

                if (success) {
                    message = "Avis envoyé avec succès!";
                } else {
                    message = "Erreur lors de l'envoi de l'avis via l'API";
                }
            } catch (Exception e) {
                message = "Erreur: " + e.getMessage();
                Log.e(TAG, "Exception lors de l'envoi de l'avis: " + e.getMessage());
            }

            // Variables finales pour le runOnUiThread
            final boolean finalSuccess = success;
            final String finalMessage = message;

            // Mettre à jour l'interface sur le thread principal
            runOnUiThread(() -> {
                btnEnvoyer.setEnabled(true);
                btnEnvoyer.setText("Envoyer");

                if (finalSuccess) {
                    Toast.makeText(this, finalMessage, Toast.LENGTH_SHORT).show();
                    finish(); // Fermer l'activité après succès
                } else {
                    Toast.makeText(this, finalMessage, Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }

    /**
     * Formate une date du format YYYY-MM-DD au format JJ mois
     *
     * @param dateStr Date au format YYYY-MM-DD
     * @return Date au format JJ mois
     */
    private String formatSimpleDate(String dateStr) {
        try {
            // Convertir 2024-12-23 en 23 décembre
            String[] parts = dateStr.split("-");
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            String[] months = {"", "janvier", "février", "mars", "avril", "mai", "juin",
                    "juillet", "août", "septembre", "octobre", "novembre", "décembre"};

            return day + " " + months[month];
        } catch (Exception e) {
            return dateStr; // En cas d'erreur, retourner la date d'origine
        }
    }

    // Gère le résultat de la prise de photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Photo prise avec succès
            Toast.makeText(this, "Photo ajoutée!", Toast.LENGTH_SHORT).show();
        }
    }

    // Gère la réponse à la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ouvrirAppareilPhoto();  // Permission accordée
            } else {
                Toast.makeText(this, "La permission est nécessaire pour prendre une photo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Ouvre l'appareil photo
    private void ouvrirAppareilPhoto() {
        Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Vérifie si une appli peut gérer l'intent
        if (intentPhoto.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentPhoto, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this,
                    "Aucune application photo trouvée.",
                    Toast.LENGTH_LONG).show();

            try {
                Intent playStoreIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=net.sourceforge.opencamera"));
                startActivity(playStoreIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Ouvrez le Play Store et installez une appli photo", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
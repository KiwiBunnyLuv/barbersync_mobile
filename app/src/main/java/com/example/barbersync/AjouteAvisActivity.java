/****************************************
 Fichier : AjoutAvisActivity.java
 Auteur : Ramin Amiri
 Fonctionnalité : MOBAVIS1 - Permet à l'utilisateur d'ajouter un avis après un rendez-vous
 Date : 2025-05-27

 Vérification :
 2025-05-28     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.os.Bundle;
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

    private TextView tvTitreCoiffeur, tvInfosRdv;
    private ImageButton btnRetour;
    private EditText etCommentaire;
    private Button btnAnnuler, btnEnvoyer, btnJoindrePhoto;
    private ImageView[] etoiles = new ImageView[5];
    private int noteActuelle = 0;
    private RendezVous rendezVous;

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
     * ou crée des données de démonstration
     */
    private void recupererRendezVous() {
        // Essayer de récupérer le rendez-vous de l'intent
        rendezVous = (RendezVous) getIntent().getSerializableExtra("rendezVous");

        // Si aucun rendez-vous n'a été passé, créer des données de démonstration
        if (rendezVous == null) {
            rendezVous = new RendezVous();
            rendezVous.setId_rendezvous(4);
            rendezVous.setNomCoiffeur("John Dow");
            rendezVous.setTypeService("Coupe régulière");
            rendezVous.setPrix(15.0);

            Creneau creneau = new Creneau(104, "15:00", "16:00", "2024-12-23");
            rendezVous.setCreneau(creneau);
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

        // Champ de commentaire
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
            Toast.makeText(this, "Fonctionnalité non disponible pour l'instant",
                    Toast.LENGTH_SHORT).show();
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
     * Envoie l'avis (actuellement simulé)
     */
    private void envoyerAvis() {
        String commentaire = etCommentaire.getText().toString().trim();

        // Vérification que l'utilisateur a sélectionné une note
        if (noteActuelle == 0) {
            Toast.makeText(this, "Veuillez sélectionner une note", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérification que l'utilisateur a entré un commentaire
        if (commentaire.isEmpty()) {
            Toast.makeText(this, "Veuillez ajouter un commentaire", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ici, en version finale, on enverrait l'avis au serveur
        // Pour l'instant, on affiche juste un toast de confirmation
        Toast.makeText(this, "Votre avis a été envoyé avec succès!", Toast.LENGTH_LONG).show();

        // Fermer l'activité
        finish();
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
}
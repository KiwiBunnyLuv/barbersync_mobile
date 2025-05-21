/****************************************
 Fichier : AvisCoiffeurActivity.java
 Auteur : Ramin Amiri
 Fonctionnalité : MOBAVIS2 - Affiche la liste des avis pour un coiffeur spécifique
 Date : 2025-05-28

 Vérification :
 2025-05-29     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activité qui affiche tous les avis pour un coiffeur spécifique.
 * Les avis sont actuellement codés en dur car l'authentification
 * n'est pas encore complètement configurée.
 */
public class AvisCoiffeurActivity extends AppCompatActivity {

    private LinearLayout containerAvis;
    private ImageButton btnRetour;
    private TextView tvTitreCoiffeur;
    private Coiffeur coiffeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis_coiffeur);

        // Récupération du coiffeur depuis l'intent
        coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");
        if (coiffeur == null) {
            // Créer un coiffeur par défaut si aucun n'est passé
            coiffeur = new Coiffeur(1, "Jack Nack", "Expert en coupes modernes.");
        }

        // Initialisation des vues
        initialiserVues();

        // Chargement des avis (codés en dur)
        chargerAvis();

        // Configuration du bouton retour
        btnRetour.setOnClickListener(v -> finish());
    }

    /**
     * Initialise les références aux vues de l'activité
     */
    private void initialiserVues() {
        containerAvis = findViewById(R.id.containerAvis);
        btnRetour = findViewById(R.id.btnRetour);
        tvTitreCoiffeur = findViewById(R.id.tvTitreCoiffeur);

        // Définir le titre avec le nom du coiffeur
        tvTitreCoiffeur.setText("Avis sur " + coiffeur.getName());
    }

    /**
     * Charge les avis codés en dur et les affiche
     */
    private void chargerAvis() {
        List<Avis> listeAvis = creerAvisDemonstration();

        LayoutInflater inflater = LayoutInflater.from(this);

        for (Avis avis : listeAvis) {
            View avisView = inflater.inflate(R.layout.item_avis, containerAvis, false);

            // Récupérer les vues dans le layout de l'avis
            TextView tvUsername = avisView.findViewById(R.id.tvUsername);
            LinearLayout etoilesContainer = avisView.findViewById(R.id.etoilesContainer);
            TextView tvCommentaire = avisView.findViewById(R.id.tvCommentaire);
            ImageView imgCoupe = avisView.findViewById(R.id.imgCoupe);

            // Définir les données de l'avis
            tvUsername.setText(avis.getUsername());
            tvCommentaire.setText(avis.getCommentaire());

            // Définir l'image
            imgCoupe.setImageResource(R.drawable.scissors);

            // Afficher les étoiles selon la note
            afficherEtoiles(etoilesContainer, avis.getNote());

            // Ajouter la vue de l'avis au conteneur
            containerAvis.addView(avisView);
        }
    }

    /**
     * Affiche les étoiles correspondant à la note donnée
     *
     * @param container Le conteneur où afficher les étoiles
     * @param note La note à représenter (de 1 à 5)
     */
    private void afficherEtoiles(LinearLayout container, int note) {
        container.removeAllViews();

        for (int i = 0; i < 5; i++) {
            ImageView etoile = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd(8);
            etoile.setLayoutParams(params);

            // Définir l'image de l'étoile selon la note
            if (i < note) {
                etoile.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                etoile.setImageResource(android.R.drawable.btn_star_big_off);
            }

            container.addView(etoile);
        }
    }

    /**
     * Crée une liste d'avis de démonstration
     *
     * @return Liste d'avis codés en dur
     */
    private List<Avis> creerAvisDemonstration() {
        List<Avis> avis = new ArrayList<>();

        avis.add(new Avis("user123", 5, "Super coupe! Prix raisonnable, je vais y revenir c'est sûr!"));
        avis.add(new Avis("user321", 2, "Pas la meilleur... au moins c'est pas cher."));
        avis.add(new Avis("user12345", 4, "Coiffeur incroyable!"));

        return avis;
    }

    /**
     * Classe interne pour représenter un avis
     */
    private static class Avis {
        private String username;
        private int note;
        private String commentaire;

        public Avis(String username, int note, String commentaire) {
            this.username = username;
            this.note = note;
            this.commentaire = commentaire;
        }

        public String getUsername() {
            return username;
        }

        public int getNote() {
            return note;
        }

        public String getCommentaire() {
            return commentaire;
        }
    }
}
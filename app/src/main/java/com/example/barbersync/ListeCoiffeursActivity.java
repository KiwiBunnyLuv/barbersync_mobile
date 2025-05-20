/****************************************
 Fichier : ListeCoiffeursActivity.java
 Auteur : Samit Sabah Adelyar
 Fonctionnalité : MOBSER1 (lister les coiffeurs)
 Date : 2025-05-11


 Vérification :
 2025-05-12     Nicolas Beaudoin        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-15     Ramin Amiri         aide pour la barre de navigation
 2025-05-20     Samit Adelyar          ajout de commentaires
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListeCoiffeursActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCoiffeurs;
    private ImageButton btnRetour;
    private List<Coiffeur> listeCoiffeurs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_coiffeurs);

        try {
            initialiserVues();

            //topbar et navbar
            configurerBoutonRetour();
            configurerNavBar();

            chargerCoiffeurs();
        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'initialisation: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //initialiser les variable de vues
    private void initialiserVues() {
        recyclerViewCoiffeurs = findViewById(R.id.recyclerViewCoiffeurs);
        btnRetour = findViewById(R.id.btnRetour);

        recyclerViewCoiffeurs.setLayoutManager(new LinearLayoutManager(this));
    }

    //configurer bouton retour en haut de l'écran
    private void configurerBoutonRetour() {
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }
    }

    //configuration de la nav bar
    private void configurerNavBar() {
        // Navigation vers l'accueil
        ImageButton homeButton = findViewById(R.id.nav_home);
        if (homeButton != null) {
            homeButton.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(this, DashBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Erreur de navigation: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Navigation vers le profil
        ImageButton profileButton = findViewById(R.id.nav_profile);
        if (profileButton != null) {
            profileButton.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Erreur de navigation: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //methode qui charge les coiffeur
    private void chargerCoiffeurs() {
        // Afficher un indicateur de chargement
        View progressBar = findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        new Thread(() -> {
            try {
                Database db = new Database(this);
                listeCoiffeurs = db.getAllCoiffeurs();


                //c'est ici qu'on met les photos et les coupes dans l'objet des coiffeurs
                for(Coiffeur c : listeCoiffeurs)
                {
                    List<Photo> photos = db.getPhotosByCoiffeur(c.getId());
                    c.setPhotos(photos);
                    List<CoupeCoiffeur> coupe = db.getCoupesByCoiffeur(c.getId());
                    c.setCoupes(coupe);
                }

                runOnUiThread(() -> {
                    if (listeCoiffeurs != null && !listeCoiffeurs.isEmpty()) {
                        CoiffeurListAdapter adapter = new CoiffeurListAdapter(this, listeCoiffeurs); //appelle de coiffeurListAdapter
                        recyclerViewCoiffeurs.setAdapter(adapter);
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(this, "Aucun coiffeur trouvé", Toast.LENGTH_SHORT).show();
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

}
/****************************************
 Fichier : HistoriqueRendezVousActivity.java
 Auteur : Ramin Amiri
 Fonctionnalité : MOBHIST1 - Affiche l'historique des rendez-vous de l'utilisateur
 Date : 2025-05-25

 Vérification :
 2025-05-26     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-26     Ramin Amiri           Ajout de la séparation entre rendez-vous à venir et passés
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Activité qui affiche l'historique des rendez-vous de l'utilisateur,
 * séparés entre les rendez-vous à venir et les rendez-vous passés.
 */
public class HistoriqueRendezVousActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProchains, recyclerViewAnciens;
    private TextView tvTitreProchain, tvTitreAnciens;
    private ImageButton btnRetour;

    private List<RendezVous> tousLesRendezVous = new ArrayList<>();
    private List<RendezVous> prochainsRendezVous = new ArrayList<>();
    private List<RendezVous> anciensRendezVous = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_rendez_vous);

        try {
            initialiserVues();
            configurerBoutonRetour();

            // Charger les données de démonstration
            chargerRendezVousDemonstration();

            // Trier les rendez-vous en prochains et anciens
            trierRendezVous();

            // Mettre à jour l'interface utilisateur
            mettreAJourUI();
        } catch (Exception e) {
            Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * Initialise les références aux vues de l'activité
     */
    private void initialiserVues() {
        recyclerViewProchains = findViewById(R.id.recyclerViewProchains);
        recyclerViewAnciens = findViewById(R.id.recyclerViewAnciens);
        tvTitreProchain = findViewById(R.id.tvTitreProchain);
        tvTitreAnciens = findViewById(R.id.tvTitreAnciens);
        btnRetour = findViewById(R.id.btnRetour);

        // Configuration des RecyclerView
        recyclerViewProchains.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnciens.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Configure le bouton de retour pour terminer l'activité
     */
    private void configurerBoutonRetour() {
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }
    }

    /**
     * Charge les données de démonstration pour les rendez-vous
     */
    private void chargerRendezVousDemonstration() {
        // Créer quelques rendez-vous de démonstration

        // Rendez-vous à venir
        RendezVous rdv1 = new RendezVous();
        rdv1.setId_rendezvous(1);
        rdv1.setCreneau(new Creneau(101, "11:15", "12:15", "2025-04-10"));
        rdv1.setNomCoiffeur("Jack Nack");
        rdv1.setTypeService("Coupe régulière");
        rdv1.setPrix(15.0);
        rdv1.setEtatRendezVous(false); // À venir

        // Rendez-vous passés
        RendezVous rdv2 = new RendezVous();
        rdv2.setId_rendezvous(2);
        rdv2.setCreneau(new Creneau(102, "13:45", "14:45", "2025-03-10"));
        rdv2.setNomCoiffeur("Jack Nack");
        rdv2.setTypeService("Coupe régulière");
        rdv2.setPrix(15.0);
        rdv2.setEtatRendezVous(true); // Terminé

        RendezVous rdv3 = new RendezVous();
        rdv3.setId_rendezvous(3);
        rdv3.setCreneau(new Creneau(103, "10:30", "11:30", "2025-01-15"));
        rdv3.setNomCoiffeur("John Dow");
        rdv3.setTypeService("Coupe ciseau");
        rdv3.setPrix(30.0);
        rdv3.setEtatRendezVous(true); // Terminé

        RendezVous rdv4 = new RendezVous();
        rdv4.setId_rendezvous(4);
        rdv4.setCreneau(new Creneau(104, "15:00", "16:00", "2024-12-23"));
        rdv4.setNomCoiffeur("Jack Nack");
        rdv4.setTypeService("Coupe régulière + barbe");
        rdv4.setPrix(18.0);
        rdv4.setEtatRendezVous(true); // Terminé

        RendezVous rdv5 = new RendezVous();
        rdv5.setId_rendezvous(5);
        rdv5.setCreneau(new Creneau(105, "10:30", "11:30", "2025-02-24"));
        rdv5.setNomCoiffeur("John Dow");
        rdv5.setTypeService("Coupe ciseau");
        rdv5.setPrix(30.0);
        rdv5.setEtatRendezVous(true); // Terminé

        RendezVous rdv6 = new RendezVous();
        rdv6.setId_rendezvous(6);
        rdv6.setCreneau(new Creneau(106, "13:00", "14:00", "2024-11-12"));
        rdv6.setNomCoiffeur("John Dow");
        rdv6.setTypeService("Coupe régulière + barbe");
        rdv6.setPrix(18.0);
        rdv6.setEtatRendezVous(true); // Terminé

        // Ajouter tous les rendez-vous à la liste
        tousLesRendezVous.add(rdv1);
        tousLesRendezVous.add(rdv2);
        tousLesRendezVous.add(rdv3);
        tousLesRendezVous.add(rdv4);
        tousLesRendezVous.add(rdv5);
        tousLesRendezVous.add(rdv6);
    }

    /**
     * Trie les rendez-vous en deux catégories : à venir et passés
     */
    private void trierRendezVous() {
        prochainsRendezVous.clear();
        anciensRendezVous.clear();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date aujourdhui = new Date();

        for (RendezVous rdv : tousLesRendezVous) {
            try {
                if (rdv.getCreneau() == null) {
                    continue; // Ignorer les rendez-vous sans créneau
                }

                Date dateRdv = sdf.parse(rdv.getCreneau().getDate());
                if (dateRdv != null && (dateRdv.after(aujourdhui) || rdv.isEtatRendezVous() == false)) {
                    prochainsRendezVous.add(rdv);
                } else {
                    anciensRendezVous.add(rdv);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                // En cas d'erreur, classer le rendez-vous par défaut dans les prochains
                if (rdv.getCreneau() != null) {
                    prochainsRendezVous.add(rdv);
                }
            }
        }
    }

    /**
     * Met à jour l'interface utilisateur avec les rendez-vous triés
     */
    private void mettreAJourUI() {
        try {
            // Adapter pour les prochains rendez-vous
            if (!prochainsRendezVous.isEmpty()) {
                RendezVousAdapter adapterProchains = new RendezVousAdapter(this, prochainsRendezVous, true);
                recyclerViewProchains.setAdapter(adapterProchains);
                tvTitreProchain.setVisibility(View.VISIBLE);
                recyclerViewProchains.setVisibility(View.VISIBLE);
            } else {
                tvTitreProchain.setVisibility(View.GONE);
                recyclerViewProchains.setVisibility(View.GONE);
            }

            // Adapter pour les anciens rendez-vous
            if (!anciensRendezVous.isEmpty()) {
                RendezVousAdapter adapterAnciens = new RendezVousAdapter(this, anciensRendezVous, false);
                recyclerViewAnciens.setAdapter(adapterAnciens);
                tvTitreAnciens.setVisibility(View.VISIBLE);
                recyclerViewAnciens.setVisibility(View.VISIBLE);
            } else {
                tvTitreAnciens.setVisibility(View.GONE);
                recyclerViewAnciens.setVisibility(View.GONE);
            }

            // Si aucun rendez-vous n'est trouvé, afficher un message
            if (prochainsRendezVous.isEmpty() && anciensRendezVous.isEmpty()) {
                Toast.makeText(this, "Vous n'avez pas de rendez-vous", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'affichage des rendez-vous: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
/****************************************
 * Fichier : CreneauAdapter.java
 * Auteur : Ramin Amiri
 * Fonctionnalité : MOBDISPONIBILITE - Adaptateur pour afficher les créneaux disponibles
 * Date : 2025-05-28
 *
 * Vérification :
 * 2025-05-28     Samit Adelyar        Approuvé
 =========================================================
 * Historique de modifications :
 * 2025-05-28     Ramin Amiri           Correction de l'affichage et gestion d'erreurs
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adaptateur pour afficher les créneaux disponibles d'un coiffeur
 * pour la prise de rendez-vous
 */
public class CreneauAdapter extends RecyclerView.Adapter<CreneauAdapter.CreneauViewHolder> {

    private static final String TAG = "CreneauAdapter";

    // Variables de données
    private Context context;
    private List<Creneau> creneaux;
    private Coiffeur coiffeur;

    /**
     * Constructeur de l'adaptateur
     * @param context Contexte de l'activité
     * @param creneaux Liste des créneaux à afficher
     * @param coiffeur Coiffeur sélectionné
     */
    public CreneauAdapter(Context context, List<Creneau> creneaux, Coiffeur coiffeur) {
        this.context = context;
        this.creneaux = creneaux;
        this.coiffeur = coiffeur;
    }

    @NonNull
    @Override
    public CreneauViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Utiliser le layout existant pour les créneaux
        View view = LayoutInflater.from(context).inflate(R.layout.item_heure_disponible, parent, false);
        return new CreneauViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreneauViewHolder holder, int position) {
        try {
            Creneau creneau = creneaux.get(position);

            // Formater l'affichage avec la date et l'heure
            String dateFormatee = formaterDate(creneau.getDate());
            String heureComplete = creneau.getHeureDebut() + " - " + creneau.getHeureFin();

            // Afficher la date et l'heure ensemble
            String affichageComplet = dateFormatee + "\n" + heureComplete;
            holder.tvHeure.setText(affichageComplet);

            // Configurer le clic sur le créneau
            holder.itemView.setOnClickListener(v -> {
                try {
                    // Rediriger vers la page de confirmation
                    Intent intent = new Intent(context, ConfirmationRendezVousActivity.class);
                    intent.putExtra("creneau", creneau);
                    intent.putExtra("coiffeur", coiffeur);
                    context.startActivity(intent);

                } catch (Exception e) {
                    Log.e(TAG, "Erreur lors de la sélection du créneau: " + e.getMessage());
                    Toast.makeText(context, "Erreur lors de la sélection", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Erreur dans onBindViewHolder: " + e.getMessage());
            // Afficher un message d'erreur basique
            holder.tvHeure.setText("Erreur d'affichage");
        }
    }

    @Override
    public int getItemCount() {
        return creneaux.size();
    }

    /**
     * Formate une date du format YYYY-MM-DD vers un format plus lisible
     * @param dateStr Date au format YYYY-MM-DD
     * @return Date formatée en français
     */
    private String formaterDate(String dateStr) {
        try {
            // Parser la date
            SimpleDateFormat formatEntree = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = formatEntree.parse(dateStr);

            // Formater en français
            SimpleDateFormat formatSortie = new SimpleDateFormat("EEEE dd MMMM", Locale.FRENCH);
            String dateFormatee = formatSortie.format(date);

            // Mettre la première lettre en majuscule
            return dateFormatee.substring(0, 1).toUpperCase() + dateFormatee.substring(1);

        } catch (ParseException e) {
            return dateStr; // Retourner la date originale en cas d'erreur
        }
    }

    /**
     * ViewHolder pour les éléments de créneau
     */
    public static class CreneauViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeure;

        public CreneauViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeure = itemView.findViewById(R.id.tvHeure);
        }
    }
}
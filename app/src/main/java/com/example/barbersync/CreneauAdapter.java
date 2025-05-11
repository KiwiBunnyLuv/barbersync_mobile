package com.example.barbersync;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CreneauAdapter extends RecyclerView.Adapter<CreneauAdapter.CreneauViewHolder> {

    private Context context;
    private Map<String, List<Creneau>> creneauxParJour; // Map organisée par date
    private Coiffeur coiffeur;
    private String[] jours; // Tableau des jours pour l'accès par position

    public CreneauAdapter(Context context, List<CreneauCoiffeur> creneauxCoiffeur, Coiffeur coiffeur) {
        this.context = context;
        this.coiffeur = coiffeur;

        // Organiser les créneaux par jour
        creneauxParJour = new TreeMap<>(); // TreeMap pour garder l'ordre des dates

        for (CreneauCoiffeur cc : creneauxCoiffeur) {
            if (cc.getCoiffeurs() == coiffeur.getId() && cc.getDispo() == 1 && cc.getReserve() == 0) {
                // Trouver le créneau correspondant
                for (Creneau c : ((DisponibilitesActivity)context).getCreneaux()) {
                    if (c.getId() == cc.getCreneau()) {
                        String jour = c.getDate();
                        if (!creneauxParJour.containsKey(jour)) {
                            creneauxParJour.put(jour, new java.util.ArrayList<>());
                        }
                        creneauxParJour.get(jour).add(c);
                        break;
                    }
                }
            }
        }

        // Créer le tableau des jours
        jours = creneauxParJour.keySet().toArray(new String[0]);
    }

    @NonNull
    @Override
    public CreneauViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jour_creneau, parent, false);
        return new CreneauViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreneauViewHolder holder, int position) {
        String jour = jours[position];
        List<Creneau> creneauxDuJour = creneauxParJour.get(jour);

        // Définir le jour
        holder.jourTextView.setText(formatJour(jour));

        // Supprimer tous les boutons précédents
        holder.creneauxLayout.removeAllViews();

        // Ajouter dynamiquement des boutons pour chaque créneau
        for (Creneau creneau : creneauxDuJour) {
            Button btnCreneau = new Button(context);
            btnCreneau.setText(creneau.getHeureDebut());
            btnCreneau.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            btnCreneau.setTextColor(context.getResources().getColor(android.R.color.black));

            // Ajouter des marges
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);
            btnCreneau.setLayoutParams(params);

            // Définir l'action du bouton
            btnCreneau.setOnClickListener(v -> {
                Intent intent = new Intent(context, ConfirmationRendezVousActivity.class);
                intent.putExtra("creneau", creneau);
                intent.putExtra("coiffeur", coiffeur);
                context.startActivity(intent);
            });

            holder.creneauxLayout.addView(btnCreneau);
        }
    }

    @Override
    public int getItemCount() {
        return creneauxParJour.size();
    }

    // Méthode pour formater la date en un format plus lisible
    private String formatJour(String date) {
        // Format de date attendu: "2025-05-06"
        // On peut améliorer l'affichage ici si nécessaire
        String[] parties = date.split("-");
        if (parties.length == 3) {
            int jour = Integer.parseInt(parties[2]);
            int mois = Integer.parseInt(parties[1]);

            // Tableau des noms de mois en français
            String[] nomsMois = {"", "janvier", "février", "mars", "avril", "mai", "juin",
                    "juillet", "août", "septembre", "octobre", "novembre", "décembre"};

            String nomJour = obtenirNomJour(date);
            return nomJour + " " + jour + " " + nomsMois[mois];
        }
        return date;
    }

    // Méthode pour obtenir le nom du jour de la semaine (lundi, mardi, etc.)
    private String obtenirNomJour(String dateStr) {
        try {
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = format.parse(dateStr);
            java.text.SimpleDateFormat formatJour = new java.text.SimpleDateFormat("EEEE", java.util.Locale.FRENCH);
            String jour = formatJour.format(date);
            // Première lettre en majuscule
            return jour.substring(0, 1).toUpperCase() + jour.substring(1);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static class CreneauViewHolder extends RecyclerView.ViewHolder {
        TextView jourTextView;
        ViewGroup creneauxLayout;

        public CreneauViewHolder(@NonNull View itemView) {
            super(itemView);
            jourTextView = itemView.findViewById(R.id.jourTextView);
            creneauxLayout = itemView.findViewById(R.id.creneauxLayout);
        }
    }
}
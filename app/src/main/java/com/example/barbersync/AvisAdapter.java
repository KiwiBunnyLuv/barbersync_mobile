/****************************************
 Fichier : AvisAdapter.java
 Auteur : Ramin Amiri
 Fonctionnalité : MOBAVIS2 - Adapter pour afficher les avis d'un coiffeur
 Date : 2025-05-30

 Vérification :
 2025-05-30     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adaptateur pour afficher les avis d'un coiffeur dans un RecyclerView.
 */
public class AvisAdapter extends RecyclerView.Adapter<AvisAdapter.AvisViewHolder> {

    private final Context context;
    private final List<Avis> avis;

    /**
     * Constructeur de l'adaptateur
     * @param context Contexte de l'application
     * @param avis Liste des avis à afficher
     */
    public AvisAdapter(Context context, List<Avis> avis) {
        this.context = context;
        this.avis = avis;
    }

    @NonNull
    @Override
    public AvisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_avis, parent, false);
        return new AvisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvisViewHolder holder, int position) {
        Avis avisItem = avis.get(position);

        // Définir le nom d'utilisateur
        holder.tvUsername.setText(avisItem.getUsername());

        // Définir le titre de l'avis
        holder.tvTitre.setText(avisItem.getTitre());

        // Définir le commentaire
        holder.tvCommentaire.setText(avisItem.getCommentaire());

        // Afficher les étoiles selon la note - assurer que la note est valide
        int note = avisItem.getNote();
        if (note < 1) note = 1; // Au minimum 1 étoile
        if (note > 5) note = 5; // Au maximum 5 étoiles

        afficherEtoiles(holder.etoilesContainer, note);

        // Définir l'image si disponible
        if (avisItem.getCheminPhoto() != null && !avisItem.getCheminPhoto().isEmpty()) {
            // En phase de développement, on utilise simplement une image par défaut
            holder.imgCoupe.setImageResource(R.drawable.scissors);
            holder.imgCoupe.setVisibility(View.VISIBLE);
        } else {
            holder.imgCoupe.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return avis.size();
    }

    /**
     * Affiche les étoiles correspondant à la note donnée
     *
     * @param container Le conteneur où afficher les étoiles
     * @param note La note à représenter (de 1 à 5)
     */
    private void afficherEtoiles(LinearLayout container, int note) {
        container.removeAllViews();

        // S'assurer que la note est entre 1 et 5
        int noteSanitized = Math.max(1, Math.min(5, note));

        // Utiliser un layout plus compact
        container.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Taille des étoiles en dp
        int sizeDp = 20;
        // Convertir dp en pixels
        float density = context.getResources().getDisplayMetrics().density;
        int sizeInPixels = (int) (sizeDp * density);

        // Espacement entre les étoiles
        int marginDp = 2;
        int marginInPixels = (int) (marginDp * density);

        for (int i = 0; i < 5; i++) {
            ImageView etoile = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    sizeInPixels,
                    sizeInPixels);
            params.setMarginEnd(marginInPixels);
            etoile.setLayoutParams(params);

            // Définir l'image de l'étoile selon la note
            if (i < noteSanitized) {
                etoile.setImageResource(R.drawable.etoile_pleine);
            } else {
                etoile.setImageResource(R.drawable.etoile_vide);
            }

            // Assurer que l'image remplit l'ImageView correctement
            etoile.setScaleType(ImageView.ScaleType.FIT_CENTER);
            etoile.setAdjustViewBounds(true);

            container.addView(etoile);
        }
    }

    /**
     * ViewHolder pour les éléments d'avis
     */
    public static class AvisViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvTitre, tvCommentaire;
        ImageView imgCoupe;
        LinearLayout etoilesContainer;

        public AvisViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTitre = itemView.findViewById(R.id.tvTitre);
            tvCommentaire = itemView.findViewById(R.id.tvCommentaire);
            imgCoupe = itemView.findViewById(R.id.imgCoupe);
            etoilesContainer = itemView.findViewById(R.id.etoilesContainer);
        }
    }
}
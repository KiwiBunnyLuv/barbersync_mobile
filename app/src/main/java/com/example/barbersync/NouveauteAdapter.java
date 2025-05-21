/****************************************
 Fichier : NouveauteAdapter.java
 Auteur : Nicolas Beaudoin
 Fonctionnalité : Gère l'affichage des nouveautés dans un RecyclerView
 Date : 2025-05-07

 Vérification :
 2025-05-20     Yassine Abide        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-20     Nicolas Beaudoin           Ajout de commentaires et javadoc
 =========================================================
 ****************************************/

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

/**
 * Adapter pour afficher les nouveautés dans un RecyclerView.
 */
public class NouveauteAdapter extends RecyclerView.Adapter<NouveauteAdapter.NouveauteViewHolder> {

    private Context context;
    private List<Nouveaute> nouveautes;
    /**
     * Constructeur de l'adapter.
     * @param context Contexte de l'application.
     * @param nouveautes Liste des nouveautés.
     */
    public NouveauteAdapter(Context context, List<Nouveaute> nouveautes) {
        this.context = context;
        this.nouveautes = nouveautes;
    }

    @NonNull
    @Override
    public NouveauteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nouveaute, parent, false);
        return new NouveauteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NouveauteViewHolder holder, int position) {
        Nouveaute nouveaute = nouveautes.get(position);
        holder.titre.setText(nouveaute.getNom());
        holder.description.setText(nouveaute.getDescription());
    }

    @Override
    public int getItemCount() {
        return nouveautes.size();
    }
    /**
     * ViewHolder pour gérer les éléments de chaque nouveauté.
     */
    public static class NouveauteViewHolder extends RecyclerView.ViewHolder {
        TextView titre, description;
        /**
         * Constructeur du ViewHolder.
         * @param itemView Vue de l'élément.
         */
        public NouveauteViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titre);
            description = itemView.findViewById(R.id.description);
        }
    }
}
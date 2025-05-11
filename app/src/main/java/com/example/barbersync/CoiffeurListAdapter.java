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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CoiffeurListAdapter extends RecyclerView.Adapter<CoiffeurListAdapter.CoiffeurViewHolder> {

    private static final String TAG = "CoiffeurAdapter";
    private final Context context;
    private final List<Coiffeur> coiffeurs;

    public CoiffeurListAdapter(Context context, List<Coiffeur> coiffeurs) {
        this.context = context;
        this.coiffeurs = coiffeurs;
        Log.d(TAG, "Adapter créé avec " + coiffeurs.size() + " coiffeurs");
    }

    @NonNull
    @Override
    public CoiffeurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coiffeur_list, parent, false);
        return new CoiffeurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoiffeurViewHolder holder, int position) {
        try {
            Coiffeur coiffeur = coiffeurs.get(position);
            Log.d(TAG, "Binding coiffeur: " + coiffeur.getName());

            holder.nomCoiffeur.setText(coiffeur.getName());

            // Ajouter un listener pour naviguer vers la page des disponibilités
            holder.cardView.setOnClickListener(v -> {
                try {
                    Log.d(TAG, "Click sur le coiffeur: " + coiffeur.getName());
                    Intent intent = new Intent(context, DisponibilitesActivity.class);
                    intent.putExtra("coiffeur", coiffeur);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Erreur lors de la navigation vers les disponibilités: " + e.getMessage(), e);
                    Toast.makeText(context, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors du binding: " + e.getMessage(), e);
            holder.nomCoiffeur.setText("Erreur: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return coiffeurs.size();
    }

    public static class CoiffeurViewHolder extends RecyclerView.ViewHolder {
        TextView nomCoiffeur;
        CardView cardView;

        public CoiffeurViewHolder(@NonNull View itemView) {
            super(itemView);
            nomCoiffeur = itemView.findViewById(R.id.nomCoiffeur);
            cardView = (CardView) itemView;
        }
    }
}
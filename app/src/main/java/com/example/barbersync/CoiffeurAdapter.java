package com.example.barbersync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CoiffeurAdapter extends RecyclerView.Adapter<CoiffeurAdapter.CoiffeurViewHolder> {

    private Context context;
    private List<Coiffeur> coiffeurs;

    public CoiffeurAdapter(Context context, List<Coiffeur> coiffeurs) {
        this.context = context;
        this.coiffeurs = coiffeurs;
    }

    @NonNull
    @Override
    public CoiffeurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coiffeur, parent, false);
        return new CoiffeurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoiffeurViewHolder holder, int position) {
        Coiffeur coiffeur = coiffeurs.get(position);
        holder.name.setText(coiffeur.getName());
        holder.biographie.setText(coiffeur.getBiographie());
    }

    @Override
    public int getItemCount() {
        return coiffeurs.size();
    }

    public static class CoiffeurViewHolder extends RecyclerView.ViewHolder {
        TextView name, biographie;

        public CoiffeurViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.coiffeur_name);
            biographie = itemView.findViewById(R.id.coiffeur_biographie);
        }
    }
}
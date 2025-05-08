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


public class NouveauteAdapter extends RecyclerView.Adapter<NouveauteAdapter.NouveauteViewHolder> {

    private Context context;
    private List<Nouveaute> nouveautes;

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
        holder.titre.setText(nouveaute.getTitre());
        holder.description.setText(nouveaute.getDescription());

        holder.btnEnSavoirPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(context, NouveauteDetailActivity.class);
            intent.putExtra("titre", nouveaute.getTitre());
            intent.putExtra("description", nouveaute.getDescription());
            intent.putExtra("dateDebut", nouveaute.getDateDebut().toString());
            intent.putExtra("dateFin", nouveaute.getDateFin().toString());
            intent.putExtra("type", nouveaute.getType());
            context.startActivity(intent);
        }
        });
    }

    @Override
    public int getItemCount() {
        return nouveautes.size();
    }

    public static class NouveauteViewHolder extends RecyclerView.ViewHolder {
        TextView titre, description;
        Button btnEnSavoirPlus;

        public NouveauteViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titre);
            description = itemView.findViewById(R.id.description);
            btnEnSavoirPlus = itemView.findViewById(R.id.btn_en_savoir_plus);
        }
    }
}
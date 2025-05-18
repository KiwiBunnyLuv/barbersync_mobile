package com.example.barbersync;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder> {

    private Coiffeur coiffeur;
    private Context context;

    public ServicesAdapter(Coiffeur c) {
        this.coiffeur = c;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        if(coiffeur.getCoupes().get(position).getPrix() != 0) {
            String service = coiffeur.getCoupes().get(position).getNom() + " - " + coiffeur.getCoupes().get(position).getPrix() + " $";
            holder.textViewService.setText(service);

        }
        else{
            String service = "";
            holder.textViewService.setText(service);
        }

    }

    @Override
    public int getItemCount() {
        return coiffeur.getCoupes().size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView textViewService;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewService = itemView.findViewById(R.id.textViewService);
        }
    }
}


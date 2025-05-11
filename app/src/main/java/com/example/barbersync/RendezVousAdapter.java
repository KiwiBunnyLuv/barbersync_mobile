package com.example.barbersync;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RendezVousAdapter extends RecyclerView.Adapter<RendezVousAdapter.RendezVousViewHolder> {

    private final Context context;
    private final List<RendezVous> rendezVousList;
    private final boolean isUpcoming; // Pour différencier les RDV à venir et passés

    public RendezVousAdapter(Context context, List<RendezVous> rendezVousList, boolean isUpcoming) {
        this.context = context;
        this.rendezVousList = rendezVousList;
        this.isUpcoming = isUpcoming;
    }

    @NonNull
    @Override
    public RendezVousViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rendez_vous, parent, false);
        return new RendezVousViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RendezVousViewHolder holder, int position) {
        try {
            RendezVous rendezVous = rendezVousList.get(position);

            // Afficher les informations du coiffeur
            if (rendezVous.getNomCoiffeur() != null) {
                holder.nomCoiffeur.setText(rendezVous.getNomCoiffeur());
            } else {
                holder.nomCoiffeur.setText("Coiffeur non spécifié");
            }

            // Afficher les détails du rendez-vous
            if (rendezVous.getCreneau() != null) {
                String date = rendezVous.getCreneau().getDate();
                String heure = rendezVous.getCreneau().getHeureDebut();
                holder.dateHeure.setText(date + " - " + heure);
            } else {
                holder.dateHeure.setText("Date et heure non disponibles");
            }

            // Afficher le type de service et le prix
            if (rendezVous.getTypeService() != null) {
                holder.typeService.setText(rendezVous.getTypeService() + " - " + rendezVous.getPrix() + "$");
            } else {
                holder.typeService.setText("Service non spécifié");
            }

            // Gérer les boutons différemment selon le type de rendez-vous
            final int currentPosition = position;
            if (isUpcoming) {
                holder.btnAction.setText("Annuler");
                holder.btnAction.setOnClickListener(v -> annulerRendezVous(rendezVous, currentPosition));
            } else {
                holder.btnAction.setText("Mettre un avis");
                holder.btnAction.setOnClickListener(v -> ajouterAvis(rendezVous));
            }
        } catch (Exception e) {
            Toast.makeText(context, "Erreur lors de l'affichage du rendez-vous: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return rendezVousList.size();
    }

    // Méthode pour annuler un rendez-vous
    private void annulerRendezVous(RendezVous rendezVous, int position) {
        try {
            // Logique d'annulation avec appel API
            new Thread(() -> {
                Api api = new Api();
                boolean success = api.annulerRendezVous(rendezVous.getId_rendezvous());

                if (context instanceof HistoriqueRendezVousActivity) {
                    ((HistoriqueRendezVousActivity) context).runOnUiThread(() -> {
                        if (success) {
                            Toast.makeText(context, "Rendez-vous annulé avec succès", Toast.LENGTH_SHORT).show();
                            rendezVousList.remove(position);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Erreur lors de l'annulation du rendez-vous", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        } catch (Exception e) {
            Toast.makeText(context, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Méthode pour ajouter un avis
    private void ajouterAvis(RendezVous rendezVous) {
        try {
            // Temporairement, afficher un message au lieu de naviguer vers l'écran d'avis
            Toast.makeText(context,
                    "Fonctionnalité d'avis en cours de développement. Rendez-vous ID: " +
                            rendezVous.getId_rendezvous(),
                    Toast.LENGTH_LONG).show();

            /* Une fois que AjoutAvisActivity sera disponible, décommentez ce code:
            Intent intent = new Intent(context, AjoutAvisActivity.class);
            intent.putExtra("rendezVous", rendezVous);
            context.startActivity(intent);
            */
        } catch (Exception e) {
            Toast.makeText(context,
                    "Erreur: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static class RendezVousViewHolder extends RecyclerView.ViewHolder {
        ImageView imageCoiffeur;
        TextView nomCoiffeur, dateHeure, typeService;
        Button btnAction;

        public RendezVousViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCoiffeur = itemView.findViewById(R.id.imageCoiffeur);
            nomCoiffeur = itemView.findViewById(R.id.nomCoiffeur);
            dateHeure = itemView.findViewById(R.id.dateHeure);
            typeService = itemView.findViewById(R.id.typeService);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}
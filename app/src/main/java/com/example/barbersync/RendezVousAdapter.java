/****************************************
 Fichier : RendezVousAdapter.java
 Auteur : Ramin Amiri
 Fonctionnalité : MOBHIST1 - Adaptateur pour l'affichage des rendez-vous dans une liste
 Date : 2025-05-25

 Vérification :
 2025-05-26     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-26     Ramin Amiri           Ajout de la gestion différenciée entre rendez-vous à venir et passés
 2025-05-28     Ramin Amiri           Ajout de la navigation vers l'écran d'ajout d'avis
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

/**
 * Adaptateur pour afficher les rendez-vous dans un RecyclerView.
 * Gère deux types d'affichage : rendez-vous à venir et rendez-vous passés.
 */
public class RendezVousAdapter extends RecyclerView.Adapter<RendezVousAdapter.RendezVousViewHolder> {

    private static final String TAG = "RendezVousAdapter";
    private final Context context;
    private final List<RendezVous> rendezVousList;
    private final boolean isUpcoming; // Pour différencier les RDV à venir et passés

    /**
     * Constructeur de l'adaptateur
     *
     * @param context Contexte de l'application
     * @param rendezVousList Liste des rendez-vous à afficher
     * @param isUpcoming Indique si ce sont des rendez-vous à venir (true) ou passés (false)
     */
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
                // Convertir la date au format JJ mois (ex: 10 avril)
                date = formatSimpleDate(date);
                String heure = rendezVous.getCreneau().getHeureDebut();
                holder.dateHeure.setText(date + " - " + heure);
            } else {
                holder.dateHeure.setText("Date et heure non disponibles");
            }

            // Afficher le type de service et le prix
            String serviceInfo = "Service non spécifié";
            if (rendezVous.getTypeService() != null && !rendezVous.getTypeService().isEmpty()) {
                serviceInfo = rendezVous.getTypeService();
                if (rendezVous.getPrix() > 0) {
                    serviceInfo += " - " + rendezVous.getPrix() + "$";
                }
            }
            holder.typeService.setText(serviceInfo);

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
            Log.e(TAG, "Erreur lors de l'affichage du rendez-vous: " + e.getMessage());
            Toast.makeText(context, "Erreur d'affichage", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return rendezVousList.size();
    }

    /**
     * Formate une date du format YYYY-MM-DD au format JJ mois
     *
     * @param dateStr Date au format YYYY-MM-DD
     * @return Date au format JJ mois
     */
    private String formatSimpleDate(String dateStr) {
        try {
            // Convertir 2025-04-10 en 10 avril
            String[] parts = dateStr.split("-");
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            String[] months = {"", "janvier", "février", "mars", "avril", "mai", "juin",
                    "juillet", "août", "septembre", "octobre", "novembre", "décembre"};

            return day + " " + months[month];
        } catch (Exception e) {
            return dateStr; // En cas d'erreur, retourner la date d'origine
        }
    }

    /**
     * Annule un rendez-vous à venir avec confirmation
     *
     * @param rendezVous Le rendez-vous à annuler
     * @param position La position dans la liste
     */
    private void annulerRendezVous(RendezVous rendezVous, int position) {
        try {
            // Vérifier que le client est autorisé à annuler ce rendez-vous
            if (Client.CLIENT_COURANT == null) {
                Toast.makeText(context, "Vous devez être connecté pour annuler un rendez-vous", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Client.CLIENT_COURANT.getId() != rendezVous.getId_user()) {
                Toast.makeText(context, "Vous n'êtes pas autorisé à annuler ce rendez-vous", Toast.LENGTH_SHORT).show();
                return;
            }

            // Créer une boîte de dialogue de confirmation
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Confirmer l'annulation")
                    .setMessage("Êtes-vous sûr de vouloir annuler ce rendez-vous ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        // Procéder à l'annulation
                        procederAnnulation(rendezVous, position);
                    })
                    .setNegativeButton("Non", (dialog, which) -> {
                        // Ne rien faire, fermer la boîte de dialogue
                        dialog.dismiss();
                    })
                    .show();
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'annulation: " + e.getMessage());
            Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Procède à l'annulation effective du rendez-vous
     *
     * @param rendezVous Le rendez-vous à annuler
     * @param position La position dans la liste
     */
    private void procederAnnulation(RendezVous rendezVous, int position) {
        // Annuler le rendez-vous via l'API en arrière-plan
        new Thread(() -> {
            Api api = new Api();
            boolean success = api.annulerRendezVous(rendezVous.getId_rendezvous());

            // Mettre à jour l'UI sur le thread principal
            ((androidx.appcompat.app.AppCompatActivity) context).runOnUiThread(() -> {
                if (success) {
                    // Succès - supprimer de la liste et notifier l'adapter
                    rendezVousList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, rendezVousList.size());
                    Toast.makeText(context, "Rendez-vous annulé avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    // Échec - afficher un message d'erreur
                    Toast.makeText(context, "Erreur lors de l'annulation. Veuillez réessayer.", Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }

    /**
     * Ajoute un avis pour un rendez-vous passé
     *
     * @param rendezVous Le rendez-vous pour lequel ajouter un avis
     */
    private void ajouterAvis(RendezVous rendezVous) {
        try {
            // Vérifier que le client est autorisé à ajouter un avis pour ce rendez-vous
            if (Client.CLIENT_COURANT == null) {
                Toast.makeText(context, "Vous devez être connecté pour ajouter un avis", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Client.CLIENT_COURANT.getId() != rendezVous.getId_user()) {
                Toast.makeText(context, "Vous ne pouvez ajouter un avis que pour vos propres rendez-vous", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lancer l'activité d'ajout d'avis
            Intent intent = new Intent(context, AjouteAvisActivity.class);
            intent.putExtra("rendezVous", rendezVous);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'ajout d'avis: " + e.getMessage());
            Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ViewHolder pour les éléments de rendez-vous
     */
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
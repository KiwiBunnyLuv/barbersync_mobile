/****************************************
 Fichier : NotificationAdapter.java
 Auteur : Nicolas Beaudoin
 Fonctionnalité : Gère l'affichage des notifications dans un RecyclerView
 Date : 2025-05-13

 Vérification :
 2025-05-15     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-20     Nicolas Beaudoin     Ajout de commentaires
 2025-05-21     Nicolas Beaudoin     Modification pour supporter BaseNotification
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Adapter pour afficher les notifications dans un RecyclerView.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private final List<BaseNotification> notifications;
    private final Database db;

    /**
     * Constructeur de l'adapter.
     * @param notifications Liste des notifications génériques.
     * @param db Instance de la base de données.
     */
    public NotificationAdapter(List<BaseNotification> notifications, Database db) {
        this.notifications = notifications;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaseNotification item = notifications.get(position);
        holder.title.setText(item.getTitle());
        holder.message.setText(item.getMessage());

        // Affichage visuel selon l'état de lecture (inversé)
        if (item.isRead()) {
            // Notification lue : fond noir
            ((androidx.cardview.widget.CardView) holder.itemView)
                    .setCardBackgroundColor(android.graphics.Color.BLACK);
        } else {
            // Notification non lue : fond gris (plus clair)
            ((androidx.cardview.widget.CardView) holder.itemView)
                    .setCardBackgroundColor(android.graphics.Color.parseColor("#333333"));
        }

        holder.itemView.setOnClickListener(v -> {
            // Marquer comme lue dans l'objet
            item.setRead(true);

            // Persister le statut dans la base de données selon le type
            if (item instanceof Notification) {
                db.markNotificationAsRead(((Notification) item).getId());
            } else if (item instanceof Nouveaute) {
                db.markNouveauteAsRead(((Nouveaute) item).getId());
            }

            // Mettre à jour l'affichage
            notifyItemChanged(position);

            // Ouvre l'activité de détail
            android.content.Context context = v.getContext();
            android.content.Intent intent = new android.content.Intent(context, NotificationDetailActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("message", item.getMessage());

            // Ajouter des informations supplémentaires pour les nouveautés
            if (item instanceof Nouveaute) {
                Nouveaute nouveaute = (Nouveaute) item;
                intent.putExtra("type", nouveaute.getType());
                intent.putExtra("dates", "Du " + nouveaute.getDateDebut() + " au " + nouveaute.getDateFin());
            }

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notificationTitle);
            message = itemView.findViewById(R.id.notificationMessage);
        }
    }
}
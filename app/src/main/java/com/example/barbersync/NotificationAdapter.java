/****************************************
 Fichier : NotificationAdapter.java
 Auteur : Nicolas Beaudoin
 Fonctionnalité : Gère l'affichage des notifications dans un RecyclerView
 Date : 2025-05-13

 Vérification :
 2025-05-15     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-20     Nicolas Beaudoin           Ajout de commentaires
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
    private final List<Notification> notifications;
    private final Database db;
    /**
     * Constructeur de l'adapter.
     * @param notifications Liste des notifications.
     * @param db Instance de la base de données.
     */
    public NotificationAdapter(List<Notification> notifications, Database db) {
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
        Notification notification = notifications.get(position);
        holder.title.setText(notification.getTitle());
        holder.message.setText(notification.getMessage());

        if (notification.isRead()) {
            holder.itemView.setAlpha(0.5f); // Indique que la notification est lue
        } else {
            holder.itemView.setAlpha(1.0f);
        }

        holder.itemView.setOnClickListener(v -> {
            notification.setRead(true);
            db.markNotificationAsRead(notification.getId());
            notifyItemChanged(position);
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

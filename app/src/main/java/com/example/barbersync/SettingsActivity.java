package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        TextView nameMessage = findViewById(R.id.welcomeMessage);

        updateNotificationBadge();

        findViewById(R.id.notification).setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, NotificationActivity.class);
            startActivity(intent);
        });




        findViewById(R.id.nav_home).setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, DashBoard.class);
            startActivity(intent);
        });

        // Configuration du bouton de modification du profil
        Button modifierClientButton = findViewById(R.id.btnModifierClient);
        modifierClientButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, ModifyClientActivity.class);
            startActivity(intent);
        });

        // Configuration du bouton de rendez-vous
        Button rendezVousButton = findViewById(R.id.btnRendezVous);
        rendezVousButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, HistoriqueRendezVousActivity.class);
            startActivity(intent);
        });

        // Configuration du bouton de déconnexion
        Button logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(v -> {
            // Réinitialiser le client courant
            if (Client.CLIENT_COURANT != null) {
                Client.CLIENT_COURANT = null;
            }

            // Rediriger vers la page de connexion
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Configuration de la barre de navigation
        findViewById(R.id.nav_home).setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, DashBoard.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_scissors).setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, ListeCoiffeursActivity.class);
            startActivity(intent);
        });
    }
    private void updateNotificationBadge() {
        TextView badgeView = findViewById(R.id.notification_badge);
        if (badgeView != null) {
            // Obtenir le nombre de notifications non lues
            Database db = new Database(this);
            List<Notification> notifications = db.getAllNotifications();
            List<Nouveaute> nouveautes = db.getAllNouveautes();
            int unreadCount = 0;

            for (Notification notification : notifications) {
                if (!notification.isRead()) {
                    unreadCount++;
                }
            }

            for (Nouveaute nouveaute : nouveautes) {
                if (!nouveaute.isRead()) {
                    unreadCount++;
                }
            }

            // Mettre à jour le badge
            if (unreadCount > 0) {
                badgeView.setVisibility(View.VISIBLE);
                badgeView.setText(String.valueOf(unreadCount));
            } else {
                badgeView.setVisibility(View.GONE);
            }
        }
    }
}
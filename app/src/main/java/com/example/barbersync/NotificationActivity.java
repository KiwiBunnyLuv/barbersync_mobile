/****************************************
 Fichier : NotificationActivity.java
 Auteur : Nicolas Beaudoin
 Fonctionnalité : Affiche la liste des notifications et nouveautés
 Date : 2025-05-13

 Vérification :
 2025-05-15     Ramin Amiri        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-16     Nicolas Beaudoin     Modification des notifications pour qu'elles soient cliquables et marquées comme lues
 2025-05-20     Nicolas Beaudoin     Ajout de commentaires
 2025-05-21     Nicolas Beaudoin     Combinaison des notifications et nouveautés
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Database db = new Database(this);

        // Créer une liste combinée de BaseNotification
        List<BaseNotification> allNotifications = new ArrayList<>();

        // Ajouter les notifications standard
        List<Notification> regularNotifications = db.getAllNotifications();
        allNotifications.addAll(regularNotifications);

        // Ajouter les nouveautés comme notifications
        List<Nouveaute> nouveautes = db.getAllNouveautes();
        allNotifications.addAll(nouveautes);

        adapter = new NotificationAdapter(allNotifications, db);
        recyclerView.setAdapter(adapter);

        // Configuration de la barre de navigation
        findViewById(R.id.nav_home).setOnClickListener(v -> {
            Intent intent = new Intent(NotificationActivity.this, DashBoard.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            Intent intent = new Intent(NotificationActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_scissors).setOnClickListener(v -> {
            Intent intent = new Intent(NotificationActivity.this, ListeCoiffeursActivity.class);
            startActivity(intent);
        });
    }
}
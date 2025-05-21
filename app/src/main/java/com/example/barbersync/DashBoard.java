/****************************************
 Fichier : DashBoard.java
 Auteur : Nicolas Beaudoin
 Fonctionnalité : MOBINT1 --- Affiche le tableau de bord principal
 Date : 2025-05-13

 Vérification :
 2025-05-15     Yassine Abide      Approuvé
 =========================================================
 Historique de modifications :
 2025-05-20     Nicolas Beaudoin           Ajout de commentaires
 =========================================================
 ****************************************/

package com.example.barbersync;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DashBoard extends AppCompatActivity {

    private RecyclerView recyclerViewNouveautes;
    private NouveauteAdapter adapter;
    private final SyncManager sync = new SyncManager();
    private List<Nouveaute> nouveautes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        checkNotificationPermission();
        updateNotificationBadge();
        // Planifier le Worker pour une notification quotidienne
        scheduleDailyNotification(9,25);
        try {
            sync.synchroniserDepuisApi(this);
        }
        catch (Exception e){
            Log.e("API FAILED", "Erreur de synchronisation", e);
        }

        findViewById(R.id.notification).setOnClickListener(v -> {
            Intent intent = new Intent(DashBoard.this, NotificationActivity.class);
            startActivity(intent);
        });

        TextView welcomeMessage = findViewById(R.id.welcomeMessage);

        if (Client.CLIENT_COURANT != null) {
            welcomeMessage.setText("Bienvenue " + Client.CLIENT_COURANT.getName());
        } else {
            welcomeMessage.setText("Bienvenue Samit.");
        }
        // Pour afficher les nouveautes
        recyclerViewNouveautes = findViewById(R.id.recyclerViewNouveautes);
        recyclerViewNouveautes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //sync.synchroniserDepuisApi(this);

        Database db = new Database(this);
        nouveautes = db.getAllNouveautes();

        adapter = new NouveauteAdapter(this, nouveautes);
        recyclerViewNouveautes.setAdapter(adapter);

        // Pour afficher les coiffeurs
        RecyclerView recyclerViewCoiffeurs = findViewById(R.id.recyclerViewCoiffeurs);
        recyclerViewCoiffeurs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<Coiffeur> coiffeurs = db.getAllCoiffeurs();

        CoiffeurAdapter coiffeurAdapter = new CoiffeurAdapter(this, coiffeurs);
        recyclerViewCoiffeurs.setAdapter(coiffeurAdapter);



        // Initialisation des boutons de navigation
        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            Intent intent = new Intent(DashBoard.this, SettingsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_scissors).setOnClickListener(v -> {
            Intent intent = new Intent(DashBoard.this, ListeCoiffeursActivity.class);
            startActivity(intent);
        });
    }
    private void checkNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    private void scheduleDailyNotification(int hour, int minute) {
        Calendar currentTime = Calendar.getInstance();
        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, hour);
        targetTime.set(Calendar.MINUTE, minute);
        targetTime.set(Calendar.SECOND, 0);

        if (targetTime.before(currentTime)) {
            targetTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        long initialDelay = targetTime.getTimeInMillis() - currentTime.getTimeInMillis();

        PeriodicWorkRequest notificationWork = new PeriodicWorkRequest.Builder(
                DailyNotificationWorker.class,
                1, TimeUnit.DAYS
        )
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "DailyNotificationWork",
                ExistingPeriodicWorkPolicy.REPLACE,
                notificationWork
        );
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
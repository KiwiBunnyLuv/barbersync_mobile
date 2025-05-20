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
        // Planifier le Worker pour une notification quotidienne
        scheduleNotificationAtSpecificTime(9,0);

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
        //nouveautes.add(new Nouveaute("Nouveau coiffeur en ville!", "Découvrez notre nouveau talent.", new Date(), new Date(), true, "rabais"));
        //nouveautes.add(new Nouveaute("Offre spéciale", "20% de réduction ce mois-ci.", new Date(), new Date(), true, "promo"));
        //nouveautes.add(new Nouveaute("Offre spéciale", "20% de réduction ce mois-ci.", new Date(), new Date(), true, "promo"));

        adapter = new NouveauteAdapter(this, nouveautes);
        recyclerViewNouveautes.setAdapter(adapter);

        // Pour afficher les coiffeurs
        RecyclerView recyclerViewCoiffeurs = findViewById(R.id.recyclerViewCoiffeurs);
        recyclerViewCoiffeurs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<Coiffeur> coiffeurs = db.getAllCoiffeurs();
        //coiffeurs.add(new Coiffeur(1, "Jean Dupont", "Expert en coupes modernes."));
        //coiffeurs.add(new Coiffeur(2, "Marie Curie", "Spécialiste des coiffures classiques."));

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

    private void scheduleNotificationAtSpecificTime(int hour, int minute) {
        // Obtenez l'heure actuelle
        Calendar currentTime = Calendar.getInstance();

        // Configurez l'heure cible
        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, hour);
        targetTime.set(Calendar.MINUTE, minute);
        targetTime.set(Calendar.SECOND, 0);

        // Si l'heure cible est déjà passée aujourd'hui, planifiez pour demain
        if (targetTime.before(currentTime)) {
            targetTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Calculez le délai en millisecondes
        long delay = targetTime.getTimeInMillis() - currentTime.getTimeInMillis();

        // Créez une requête pour exécuter le Worker après le délai
        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(DailyNotificationWorker.class)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build();

        // Enfilez la requête
        WorkManager.getInstance(this).enqueue(notificationWork);
    }
}
/****************************************
 * Fichier : DailyNotificationWorker.java
 * Auteur : Nicolas Beaudoin
 * Fonctionnalité : Gère l'envoi de notifications quotidiennes
 * Date : 2025-05-20
 *
 * Vérification :
 * 2025-05-20     Yassine Abide        Approuvé
 * =========================================================
 * Historique de modifications :
 * 2025-05-20     Nicolas Beaudoin           Ajout de commentaires et javadoc
 * =========================================================
 ****************************************/
package com.example.barbersync;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
/**
 * Classe `DailyNotificationWorker` utilisée pour gérer l'envoi de notifications quotidiennes
 * via le `WorkManager`.
 */
public class DailyNotificationWorker extends Worker {

    private static final String CHANNEL_ID = "daily_notification_channel";
    /**
     * Constructeur de la classe `DailyNotificationWorker`.
     *
     * @param context       Le contexte de l'application.
     * @param workerParams  Les paramètres associés au `Worker`.
     */
    public DailyNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    /**
     * Méthode principale exécutée par le `Worker`.
     * Envoie une notification quotidienne.
     *
     * @return Un résultat indiquant le succès ou l'échec de la tâche.
     */
    @NonNull
    @Override
    public Result doWork() {
        sendNotification();
        return Result.success();
    }
    /**
     * Méthode privée pour créer et envoyer une notification.
     * Configure un canal de notification si nécessaire (Android 8.0+).
     */
    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Créer un canal de notification pour Android 8.0 et supérieur
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Daily Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Construire la notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.logo) // Remplacez par votre icône
                .setContentTitle("BarberSync")
                .setContentText("Check new offers!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Envoyer la notification
        notificationManager.notify(1, builder.build());
    }
}
package com.example.barbersync;

import android.content.Context;
import android.util.Log;

import java.util.List;


public class SyncManager {
    public void synchroniserDepuisApi(Context context)
    {
        Api api = new Api();
        Database db = new Database(context);

        new Thread(() -> {
            List<Coiffeur> coiffeurs = api.getCoiffeurs();
            if (coiffeurs != null) {
                for (Coiffeur c : coiffeurs) {
                    db.insertCoiffeur(c);
                }
                Log.d("SYNC", "Coiffeurs synchronisés : " + coiffeurs.size());
            } else {
                Log.e("SYNC", "Échec de la récupération des coiffeurs");
            }

            // Tu pourras répéter ici pour les coupes, créneaux, etc.
        }).start();

        new Thread(() -> {
            List<Coupes> Coupes = api.getCoupes();
            if (Coupes != null) {
                for (Coupes c : Coupes) {
                    db.insertCoupe(c);
                }
                Log.d("SYNC", "Coiffeurs synchronisés : " + Coupes.size());
            } else {
                Log.e("SYNC", "Échec de la récupération des coiffeurs");
            }

            // Tu pourras répéter ici pour les coupes, créneaux, etc.
        }).start();

        new Thread(() -> {
            List<Creneau> Creneaux = api.getCreneau();
            if (Creneaux != null) {
                for (Creneau c : Creneaux) {
                    db.insertCreneau(c);
                }
                Log.d("SYNC", "Coiffeurs synchronisés : " + Creneaux.size());
            } else {
                Log.e("SYNC", "Échec de la récupération des coiffeurs");
            }

            // Tu pourras répéter ici pour les coupes, créneaux, etc.
        }).start();

        new Thread(() -> {
            List<CoupeCoiffeur> coupesCoiffeurs = api.getCoupeCoiffeurs();
            if (coupesCoiffeurs != null) {
                for (CoupeCoiffeur c : coupesCoiffeurs) {
                    db.insertCoiffeurCoupe(c);
                }
                Log.d("SYNC", "Coiffeurs synchronisés : " + coupesCoiffeurs.size());
            } else {
                Log.e("SYNC", "Échec de la récupération des coiffeurs");
            }

            // Tu pourras répéter ici pour les coupes, créneaux, etc.
        }).start();
        new Thread(() -> {
            List<CreneauCoiffeur> CreneauCoiffeurs = api.getCreneauCoiffeur();
            if (CreneauCoiffeurs != null) {
                for (CreneauCoiffeur c : CreneauCoiffeurs) {
                    db.insertCoiffeurCreneau(c);
                }
                Log.d("SYNC", "Coiffeurs synchronisés : " + CreneauCoiffeurs.size());
            } else {
                Log.e("SYNC", "Échec de la récupération des coiffeurs");
            }

            // Tu pourras répéter ici pour les coupes, créneaux, etc.
        }).start();
    }
}

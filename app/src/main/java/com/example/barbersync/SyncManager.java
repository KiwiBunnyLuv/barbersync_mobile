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

            List<Coupes> Coupes = api.getCoupes();
            if (Coupes != null) {
                for (Coupes c : Coupes) {
                    db.insertCoupe(c);
                }
                Log.d("SYNC", "coupes synchronisés : " + Coupes.size());
            } else {
                Log.e("SYNC", "Échec de la récupération des coupes");
            }

            List<Creneau> Creneaux = api.getCreneau();
            if (Creneaux != null) {
                for (Creneau c : Creneaux) {
                    db.insertCreneau(c);
                }
                Log.d("SYNC", "creneaux synchronisés : " + Creneaux.size());
            } else {
                Log.e("SYNC", "Échec de la récupération des creneaux");
            }
            List<CoupeCoiffeur> coupesCoiffeurs = api.getCoupeCoiffeurs();
            if (coupesCoiffeurs != null) {
                for (CoupeCoiffeur c : coupesCoiffeurs) {
                    db.insertCoiffeurCoupe(c);
                }
                Log.d("SYNC", "coupesCoiffeurs synchronisés : " + coupesCoiffeurs.size());
            } else {
                Log.e("SYNC", "Échec de la récupération des coupescoiffeurs");
            }
            List<CreneauCoiffeur> CreneauCoiffeurs = api.getCreneauCoiffeur();
            if (CreneauCoiffeurs != null) {
                for (CreneauCoiffeur c : CreneauCoiffeurs) {
                    db.insertCoiffeurCreneau(c);
                }
                Log.d("SYNC", "creneauCoiffeurs synchronisés : " + CreneauCoiffeurs.size());
            } else {
                Log.e("SYNC", "Échec de la récupération des creneauCoiffeurs");
            }
            List<Photo> photo = api.getPhoto();
            if (photo != null) {
                for (Photo c : photo) {
                    db.insertPhoto(c);
                }
                Log.d("SYNC", "photo synchronisés : " + photo.size());
            } else {
                Log.e("SYNC", "Échec de la récupération des photos");
            }
        }).start();

    }
}

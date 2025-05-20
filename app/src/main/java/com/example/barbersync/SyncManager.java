/****************************************
 Fichier : SyncManager.java
 Auteur : Samit Sabah Adelyar
 Fonctionnalité : MOBINT1 --- classe qui fait la synchronisation entre bd local et centrale --- Samit a fait la logique générale de cette classe+ squelette
 ainsi que les synchro coiffeur, coupe, coiffeur_coupes et photos
 Date : 2025-05-13


 Vérification :
 2025-05-15     Ramin Amiri, Nicolas Beaudoin, samit adelyar, Yassine Abide        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-16     Ramin Amiri             synchro rdv et creneau avec les fonctions réliés
 2025-05-16     Ramin Amiri             synchro avis avec ses fonctions
 2025-05-16     Nicolas Beaudoin        synchro nouveautés
 2025-05-20     Samit Adelyar           ajout de commentaires et javadoc
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Context;
import android.util.Log;

import java.util.List;



public class SyncManager {
    private boolean isSyncing = false;
    public synchronized void synchroniserDepuisApi(Context context) {
        if (isSyncing) {
            Log.d("SYNC", "Synchronisation déjà en cours, annulation de l'appel.");
            return;
        }
        isSyncing = true;
        Log.e("Sync", "SYNCRONISATION");
        Api api = new Api();
        Database db = new Database(context);

        new Thread(() -> {
            try {

                List<Coiffeur> coiffeurs = api.getCoiffeurs();
                if (coiffeurs != null) {
                    for (Coiffeur c : coiffeurs) {
                        db.insertCoiffeur(c);
                    }
                    Log.d("SYNC", "Coiffeurs synchronisés : " + coiffeurs.size());
                } else {
                    Log.e("SYNC", "Échec de la récupération des coiffeurs");
                }

                List<Nouveaute> nouveautes = api.getNouveautes();
                if (nouveautes != null) {
                    for (Nouveaute n : nouveautes) {
                        db.insertNouveaute(n);
                    }
                    Log.d("SYNC", "Nouveautés synchronisées : " + nouveautes.size());
                } else {
                    Log.e("SYNC", "Échec de la récupération des nouveautés");
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


            } finally {
                isSyncing = false;
            }
        }).start();
    }
}



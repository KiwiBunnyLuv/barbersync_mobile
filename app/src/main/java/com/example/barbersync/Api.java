/****************************************
 Fichier : Api.java
 Auteur : Samit Sabah Adelyar
 Fonctionnalité : MOBINT1 --- classe qui fait tous les appels des api --- Samit a fait la logique générale de cette classe ainsi que coiffeur, coupe et photos
 Date : 2025-05-13


 Vérification :
 2025-05-15     Ramin Amiri, Nicolas Beaudoin, Samit Adelyar, Ramin Amiri        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-16     Yassine Abide           Ajout des api de client
 2025-05-16     Ramin Amiri             Ajout des api de avis
 2025-05-17     Ramin Amiri             Ajout des api de rdv
 2025-05-19     Nicolas Beaudoin         Ajout des api de nouveautés
 2025-06-05     Ramin Amiri             Nettoyage et simplification du code
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Api {
    private static final String BASE_URL = "http://192.168.2.21:5000";
    private static final String TAG = "Api";

    // Constantes pour les endpoints
    private static final String ENDPOINT_COIFFEURS = "/coiffeurs";
    private static final String ENDPOINT_NOUVEAUTES = "/nouveautes";
    private static final String ENDPOINT_COUPES = "/coupes";
    private static final String ENDPOINT_CRENEAU = "/creneau";
    private static final String ENDPOINT_CRENEAUCOIFFEUR = "/creneauCoiffeur";
    private static final String ENDPOINT_COUPECOIFFEUR = "/coupeCoiffeur";
    private static final String ENDPOINT_PHOTOS = "/photos";
    private static final String ENDPOINT_AVIS = "/avis";
    private static final String ENDPOINT_RENDEZVOUS = "/rendezvous";

    // Client HTTP réutilisable
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build();

    /**
     * Récupère la liste de tous les coiffeurs depuis l'API
     * @return Liste des coiffeurs
     */
    public List<Coiffeur> getCoiffeurs() {
        List<Coiffeur> coiffeurs = new ArrayList<>();
        Request request = new Request.Builder()
                .url(BASE_URL + ENDPOINT_COIFFEURS)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Coiffeur>>(){}.getType();
                coiffeurs = gson.fromJson(json, type);
            } else {
                Log.e(TAG, "Erreur HTTP coiffeur : " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau : " + e.getMessage());
        }
        return coiffeurs;
    }

    /**
     * Récupère la liste des nouveautés depuis l'API
     * @return Liste des nouveautés
     */
    public List<Nouveaute> getNouveautes() {
        List<Nouveaute> nouveautes = new ArrayList<>();
        Request request = new Request.Builder()
                .url(BASE_URL + ENDPOINT_NOUVEAUTES)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();

                Gson gson = new Gson();
                Type type = new TypeToken<List<Nouveaute>>() {}.getType();
                nouveautes = gson.fromJson(json, type);

                // Vérifier les valeurs nulles dans les nouveautés
                for (Nouveaute n : nouveautes) {
                    if (n.getDateDebut() == null) {
                        n.setDateDebut("");
                    }
                    if (n.getDateFin() == null) {
                        n.setDateFin("");
                    }
                }
            } else {
                Log.e(TAG, "Erreur HTTP nouveautes : " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau : " + e.getMessage());
        }

        return nouveautes;
    }

    /**
     * Récupère la liste des coupes depuis l'API
     * @return Liste des coupes
     */
    public List<Coupes> getCoupes() {
        List<Coupes> coupes = new ArrayList<>();
        Request request = new Request.Builder()
                .url(BASE_URL + ENDPOINT_COUPES)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Coupes>>(){}.getType();
                coupes = gson.fromJson(json, type);
            } else {
                Log.e(TAG, "Erreur HTTP coupes : " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau : " + e.getMessage());
        }

        return coupes;
    }

    /**
     * Récupère la liste des créneaux depuis l'API
     * @return Liste des créneaux
     */
    public List<Creneau> getCreneau() {
        List<Creneau> creneaux = new ArrayList<>();
        Request request = new Request.Builder()
                .url(BASE_URL + ENDPOINT_CRENEAU)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Creneau>>(){}.getType();
                creneaux = gson.fromJson(json, type);
            } else {
                Log.e(TAG, "Erreur HTTP créneaux : " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau : " + e.getMessage());
        }

        return creneaux;
    }

    /**
     * Récupère les relations créneaux-coiffeurs depuis l'API
     * @return Liste des relations créneaux-coiffeurs
     */
    public List<CreneauCoiffeur> getCreneauCoiffeur() {
        List<CreneauCoiffeur> relations = new ArrayList<>();
        Request request = new Request.Builder()
                .url(BASE_URL + ENDPOINT_CRENEAUCOIFFEUR)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Gson gson = new Gson();
                Type type = new TypeToken<List<CreneauCoiffeur>>(){}.getType();
                relations = gson.fromJson(json, type);
            } else {
                Log.e(TAG, "Erreur HTTP relations créneaux-coiffeurs : " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau : " + e.getMessage());
        }

        return relations;
    }

    /**
     * Récupère les relations coupes-coiffeurs depuis l'API
     * @return Liste des relations coupes-coiffeurs
     */
    public List<CoupeCoiffeur> getCoupeCoiffeurs() {
        List<CoupeCoiffeur> relations = new ArrayList<>();
        Request request = new Request.Builder()
                .url(BASE_URL + ENDPOINT_COUPECOIFFEUR)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Gson gson = new Gson();
                Type type = new TypeToken<List<CoupeCoiffeur>>(){}.getType();
                relations = gson.fromJson(json, type);
            } else {
                Log.e(TAG, "Erreur HTTP relations coupes-coiffeurs : " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau : " + e.getMessage());
        }

        return relations;
    }

    /**
     * Récupère les photos depuis l'API
     * @return Liste des photos
     */
    public List<Photo> getPhoto() {
        List<Photo> photos = new ArrayList<>();
        Request request = new Request.Builder()
                .url(BASE_URL + ENDPOINT_PHOTOS)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Photo>>(){}.getType();
                photos = gson.fromJson(json, type);
            } else {
                Log.e(TAG, "Erreur HTTP photos : " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau : " + e.getMessage());
        }

        return photos;
    }

    /**
     * Récupère les rendez-vous du client connecté
     * @return Liste des rendez-vous
     */
    public List<RendezVous> getRendezVous() {
        List<RendezVous> rendezVousList = new ArrayList<>();

        // Vérifier que le client est connecté
        if (Client.CLIENT_COURANT == null) {
            Log.e(TAG, "Aucun client connecté");
            return rendezVousList;
        }

        Request request = new Request.Builder()
                .url(BASE_URL + ENDPOINT_RENDEZVOUS)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Gson gson = new Gson();
                JsonArray jsonArray = gson.fromJson(json, JsonArray.class);

                // Récupérer l'ID du client connecté
                int clientId = Client.CLIENT_COURANT.getId();

                // Parcourir tous les éléments du tableau JSON
                for (JsonElement element : jsonArray) {
                    try {
                        JsonObject rdvJson = element.getAsJsonObject();
                        RendezVous rdv = new RendezVous();

                        // ID du rendez-vous
                        if (rdvJson.has("id_rendezvous")) {
                            rdv.setId_rendezvous(rdvJson.get("id_rendezvous").getAsInt());
                        }

                        // ID client - peut être dans différents endroits selon la structure API
                        int rdvClientId = -1;
                        if (rdvJson.has("client") && !rdvJson.get("client").isJsonNull()) {
                            if (rdvJson.get("client").isJsonObject()) {
                                JsonObject clientObj = rdvJson.getAsJsonObject("client");
                                if (clientObj.has("id_client")) {
                                    rdvClientId = clientObj.get("id_client").getAsInt();
                                } else if (clientObj.has("id")) {
                                    rdvClientId = clientObj.get("id").getAsInt();
                                }
                            } else if (rdvJson.get("client").isJsonPrimitive()) {
                                rdvClientId = rdvJson.get("client").getAsInt();
                            }
                        } else if (rdvJson.has("client_id") && !rdvJson.get("client_id").isJsonNull()) {
                            rdvClientId = rdvJson.get("client_id").getAsInt();
                        } else if (rdvJson.has("id_client") && !rdvJson.get("id_client").isJsonNull()) {
                            rdvClientId = rdvJson.get("id_client").getAsInt();
                        } else if (rdvJson.has("id_user") && !rdvJson.get("id_user").isJsonNull()) {
                            rdvClientId = rdvJson.get("id_user").getAsInt();
                        }

                        // Vérifier si ce rendez-vous est pour le client actuellement connecté
                        if (rdvClientId != clientId && rdvClientId != -1) {
                            // Si l'ID client est spécifié et différent du client actuel, ignorer ce rendez-vous
                            continue;
                        }

                        // Définir l'ID client
                        rdv.setId_user(clientId);

                        // État du rendez-vous
                        if (rdvJson.has("etatRendezVous")) {
                            rdv.setEtatRendezVous(rdvJson.get("etatRendezVous").getAsBoolean());
                        }

                        // Récupérer les données du créneau
                        if (rdvJson.has("creneau") && !rdvJson.get("creneau").isJsonNull()) {
                            JsonObject creneauObj = rdvJson.getAsJsonObject("creneau");

                            int creneauId = creneauObj.has("id_creneau") ? creneauObj.get("id_creneau").getAsInt() : 1;
                            String date = creneauObj.has("date") ? creneauObj.get("date").getAsString() : "2025-04-15";
                            String heureDebut = creneauObj.has("heure_debut") ?
                                    formaterHeure(creneauObj.get("heure_debut").getAsString()) : "10:00";
                            String heureFin = creneauObj.has("heure_fin") ?
                                    formaterHeure(creneauObj.get("heure_fin").getAsString()) : "11:00";

                            Creneau creneau = new Creneau(creneauId, heureDebut, heureFin, date);
                            rdv.setCreneau(creneau);
                        }

                        // Récupérer les données du coiffeur
                        if (rdvJson.has("coiffeur") && !rdvJson.get("coiffeur").isJsonNull()) {
                            if (rdvJson.get("coiffeur").isJsonObject()) {
                                JsonObject coiffeurObj = rdvJson.getAsJsonObject("coiffeur");
                                if (coiffeurObj.has("name")) {
                                    rdv.setNomCoiffeur(coiffeurObj.get("name").getAsString());
                                } else if (coiffeurObj.has("nom")) {
                                    rdv.setNomCoiffeur(coiffeurObj.get("nom").getAsString());
                                }
                            } else if (rdvJson.get("coiffeur").isJsonPrimitive()) {
                                // Si c'est juste le nom en texte
                                rdv.setNomCoiffeur(rdvJson.get("coiffeur").getAsString());
                            }
                        } else if (rdvJson.has("nom_coiffeur") && !rdvJson.get("nom_coiffeur").isJsonNull()) {
                            rdv.setNomCoiffeur(rdvJson.get("nom_coiffeur").getAsString());
                        }

                        // Si nom du coiffeur toujours non défini, utiliser une valeur par défaut
                        if (rdv.getNomCoiffeur() == null || rdv.getNomCoiffeur().isEmpty()) {
                            rdv.setNomCoiffeur("Coiffeur");
                        }

                        // Type de service et prix
                        if (rdvJson.has("type_service") && !rdvJson.get("type_service").isJsonNull()) {
                            rdv.setTypeService(rdvJson.get("type_service").getAsString());
                        } else if (rdvJson.has("typeService") && !rdvJson.get("typeService").isJsonNull()) {
                            rdv.setTypeService(rdvJson.get("typeService").getAsString());
                        } else {
                            rdv.setTypeService("Coupe standard");
                        }

                        if (rdvJson.has("prix") && !rdvJson.get("prix").isJsonNull()) {
                            rdv.setPrix(rdvJson.get("prix").getAsDouble());
                        } else {
                            rdv.setPrix(20.0); // Prix par défaut
                        }

                        // Vérifications finales pour s'assurer que toutes les données sont présentes
                        if (rdv.getCreneau() == null) {
                            Creneau creneauDefault = new Creneau(1, "10:00", "11:00", "2025-04-15");
                            rdv.setCreneau(creneauDefault);
                        }

                        rendezVousList.add(rdv);
                    } catch (Exception e) {
                        Log.e(TAG, "Erreur lors du traitement d'un rendez-vous: " + e.getMessage());
                        // Continuer avec le prochain rendez-vous
                    }
                }
            } else {
                Log.e(TAG, "Erreur HTTP rendez-vous : " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau rendez-vous : " + e.getMessage());
        }

        return rendezVousList;
    }

    /**
     * Formate l'heure en enlevant les secondes (HH:mm:ss -> HH:mm)
     */
    private String formaterHeure(String heureComplete) {
        if (heureComplete != null && heureComplete.length() >= 5) {
            // Prendre seulement les 5 premiers caractères (HH:mm)
            return heureComplete.substring(0, 5);
        }
        return heureComplete;
    }

    /**
     * Crée un nouveau rendez-vous via l'API
     * @param idCreneau ID du créneau
     * @param idClient ID du client
     * @param typeService Type de service
     * @param prix Prix du service
     * @param idCoiffeur ID du coiffeur
     * @return true si la création a réussi, false sinon
     */
    public boolean creerRendezVous(int idCreneau, int idClient, String typeService, double prix, int idCoiffeur) {
        // Validation du client connecté
        if (Client.CLIENT_COURANT == null) {
            Log.e(TAG, "Aucun client connecté");
            return false;
        }

        if (Client.CLIENT_COURANT.getId() != idClient) {
            Log.e(TAG, "Incohérence ID client");
            return false;
        }

        try {
            // Créer l'objet JSON pour la requête
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_creneau", idCreneau);
            jsonObject.put("id_user", idClient);
            jsonObject.put("confirmation_envoye", false);
            jsonObject.put("rappel_envoye", false);
            jsonObject.put("etatRendezVous", false);
            jsonObject.put("type_service", typeService);
            jsonObject.put("prix", prix);
            jsonObject.put("id_coiffeur", idCoiffeur);

            RequestBody body = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));

            Request request = new Request.Builder()
                    .url(BASE_URL + ENDPOINT_RENDEZVOUS)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                return response.isSuccessful();
            }
        } catch (JSONException e) {
            Log.e(TAG, "Erreur JSON: " + e.getMessage());
            return false;
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau: " + e.getMessage());
            return false;
        }
    }

    /**
     * Annule un rendez-vous
     * @param idRendezVous ID du rendez-vous à annuler
     * @return true si l'annulation a réussi, false sinon
     */
    public boolean annulerRendezVous(int idRendezVous) {
        Request request = new Request.Builder()
                .url(BASE_URL + ENDPOINT_RENDEZVOUS + "/" + idRendezVous)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère les avis pour un coiffeur spécifique
     * @param idCoiffeur ID du coiffeur dont on veut récupérer les avis
     * @return Liste d'objets Avis, ou une liste vide en cas d'erreur
     */
    public List<Avis> getAvisParCoiffeur(int idCoiffeur) {
        List<Avis> listeAvis = new ArrayList<>();

        try {
            // Récupérer tous les avis depuis l'API
            Request request = new Request.Builder()
                    .url(BASE_URL + ENDPOINT_AVIS)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonResponse = response.body().string();

                    // Parser la réponse JSON
                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.fromJson(jsonResponse, JsonArray.class);

                    // Parcourir les avis
                    for (JsonElement element : jsonArray) {
                        JsonObject avisJson = element.getAsJsonObject();

                        try {
                            // Extraire les données de l'avis
                            int idEvaluation = avisJson.has("id_evaluation") ?
                                    avisJson.get("id_evaluation").getAsInt() : 0;

                            // Vérifier si cet avis concerne notre coiffeur
                            boolean estAvisCoiffeur = false;
                            String clientName = "Client";

                            if (avisJson.has("id_rendezvous") && !avisJson.get("id_rendezvous").isJsonNull()) {
                                JsonObject rdvObj = avisJson.getAsJsonObject("id_rendezvous");

                                // Vérifier l'ID du coiffeur
                                if (rdvObj.has("coiffeur_id") && !rdvObj.get("coiffeur_id").isJsonNull()) {
                                    int coiffeurIdAvis = rdvObj.get("coiffeur_id").getAsInt();
                                    if (coiffeurIdAvis == idCoiffeur) {
                                        estAvisCoiffeur = true;

                                        // Récupérer le nom du client si disponible
                                        if (rdvObj.has("client_name") && !rdvObj.get("client_name").isJsonNull()) {
                                            clientName = rdvObj.get("client_name").getAsString();
                                        }
                                    }
                                }
                            }

                            // Si ce n'est pas un avis pour ce coiffeur, passer au suivant
                            if (!estAvisCoiffeur) {
                                continue;
                            }

                            // Récupérer les autres données de l'avis
                            String titre = avisJson.has("titre") && !avisJson.get("titre").isJsonNull() ?
                                    avisJson.get("titre").getAsString() : "Sans titre";

                            String commentaire = avisJson.has("commentaire") && !avisJson.get("commentaire").isJsonNull() ?
                                    avisJson.get("commentaire").getAsString() : "";

                            int note = avisJson.has("note_sur_5") ?
                                    avisJson.get("note_sur_5").getAsInt() : 0;

                            String cheminPhoto = avisJson.has("chemin_photo") && !avisJson.get("chemin_photo").isJsonNull() ?
                                    avisJson.get("chemin_photo").getAsString() : "";

                            String createdAt = avisJson.has("created_at") && !avisJson.get("created_at").isJsonNull() ?
                                    avisJson.get("created_at").getAsString() : "";

                            String reponse = avisJson.has("reponse") && !avisJson.get("reponse").isJsonNull() ?
                                    avisJson.get("reponse").getAsString() : null;

                            // Récupérer l'ID du rendez-vous
                            int idRendezVous = 0;
                            if (avisJson.has("id_rendezvous") && !avisJson.get("id_rendezvous").isJsonNull()) {
                                if (avisJson.get("id_rendezvous").isJsonObject()) {
                                    // L'ID est dans un objet complexe
                                    JsonObject rdvObj = avisJson.getAsJsonObject("id_rendezvous");
                                    if (rdvObj.has("id")) {
                                        idRendezVous = rdvObj.get("id").getAsInt();
                                    }
                                } else {
                                    // L'ID est directement accessible
                                    idRendezVous = avisJson.get("id_rendezvous").getAsInt();
                                }
                            }

                            // Créer l'objet Avis et l'ajouter à la liste
                            Avis avis = new Avis(idEvaluation, idRendezVous, titre, commentaire,
                                    note, clientName, cheminPhoto, createdAt, reponse);
                            listeAvis.add(avis);
                        } catch (Exception e) {
                            Log.e(TAG, "Erreur lors du parsing d'un avis: " + e.getMessage());
                        }
                    }
                } else {
                    Log.e(TAG, "Erreur HTTP avis: " + response.code());
                }
            }

        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau lors de la récupération des avis: " + e.getMessage());
        }

        return listeAvis;
    }

    /**
     * Envoie un avis à l'API Flask
     * @param idRendezVous ID du rendez-vous concerné
     * @param titre Titre de l'avis
     * @param commentaire Commentaire détaillé de l'avis
     * @param note Note sur 5 étoiles
     * @param cheminPhoto Chemin de la photo (vide si aucune photo)
     * @return true si l'avis a été enregistré avec succès, false sinon
     */
    public boolean ajouterAvis(int idRendezVous, String titre, String commentaire, int note, String cheminPhoto) {
        try {
            // Validation des paramètres
            if (idRendezVous <= 0) {
                return false;
            }

            if (titre == null || titre.trim().isEmpty()) {
                return false;
            }

            if (commentaire == null || commentaire.trim().isEmpty()) {
                return false;
            }

            if (note < 1 || note > 5) {
                return false;
            }

            // Vérifier que le client est connecté
            if (Client.CLIENT_COURANT == null) {
                return false;
            }

            // Créer l'objet JSON pour la requête
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_rendezvous", idRendezVous);
            jsonObject.put("titre", titre.trim());
            jsonObject.put("commentaire", commentaire.trim());
            jsonObject.put("note_sur_5", note);
            jsonObject.put("chemin_photo", cheminPhoto != null ? cheminPhoto : "");

            RequestBody body = RequestBody.create(
                    jsonObject.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(BASE_URL + ENDPOINT_AVIS)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                return response.isSuccessful();
            }
        } catch (JSONException e) {
            Log.e(TAG, "Erreur JSON lors de l'envoi de l'avis: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Erreur réseau lors de l'envoi de l'avis: " + e.getMessage());
        }

        return false;
    }
}
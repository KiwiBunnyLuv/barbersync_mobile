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
 2025-05-20     Samit Adelyar           ajout de commentaires
 =========================================================
 ****************************************/

package com.example.barbersync;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Api {
    //api pour tous les coiffeurs
    public List<Coiffeur> getCoiffeurs(){
        List<Coiffeur> coiffeurs = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/coiffeurs")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                Gson gson = new Gson(); // gson arrive a créer un classe a partir de json
                Type type = new TypeToken<List<Coiffeur>>(){}.getType();
                coiffeurs = gson.fromJson(json, type);
            } else {
                Log.e("API", "Erreur HTTP coiffeur : " + response.code());

            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }
        Log.e("API", "Nombre de coiffeurs récupérés : " + coiffeurs);
        return coiffeurs;
    }

    // api pour tous les nouveautes
    public List<Nouveaute> getNouveautes() {
        List<Nouveaute> nouveautes = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/nouveautes")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                Gson gson = new Gson();  // gson arrive a créer un classe a partir de json
                Type type = new TypeToken<List<Nouveaute>>() {}.getType();
                nouveautes = gson.fromJson(json, type);
            } else {
                Log.e("API", "Erreur HTTP nouveates : " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }

        return nouveautes;
    }

    //api pour prendre tous les coiffeurs
    public List<Coupes> getCoupes(){
        List<Coupes> Coupes = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/coupes")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                Gson gson = new Gson();  // gson arrive a créer un classe a partir de json
                Type type = new TypeToken<List<Coupes>>(){}.getType();
                Log.e("info", "coupe: "+  gson.fromJson(json, type));
                Coupes = gson.fromJson(json, type);
            } else {
                Log.e("API", "Erreur HTTP coupe: " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }

        return Coupes;
    }
    //api pour prendre tous les creneaux
    public List<Creneau> getCreneau(){
        List<Creneau> Creneau = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/creneau")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                Gson gson = new Gson();  // gson arrive a créer un classe a partir de json
                Type type = new TypeToken<List<Creneau>>(){}.getType();
                Creneau = gson.fromJson(json, type);
            } else {
                Log.e("API", "Erreur HTTP : " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }

        return Creneau;
    }
    //api pour prendre tous les creneauCoiffeur
    public List<CreneauCoiffeur> getCreneauCoiffeur(){
        List<CreneauCoiffeur> CreneauCoiffeur = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/creneauCoiffeur")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                Gson gson = new Gson();  // gson arrive a créer un classe a partir de json
                Type type = new TypeToken<List<CreneauCoiffeur>>(){}.getType();
                CreneauCoiffeur = gson.fromJson(json, type);
            } else {
                Log.e("API", "Erreur HTTP : " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }

        return CreneauCoiffeur;
    }

    //api pour prendre tous les coupeCoiffeurs
    public List<CoupeCoiffeur> getCoupeCoiffeurs(){
        List<CoupeCoiffeur> CoupeCoiffeur = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/coupeCoiffeur")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                Gson gson = new Gson();  // gson arrive a créer un classe a partir de json
                Type type = new TypeToken<List<CoupeCoiffeur>>(){}.getType();
                CoupeCoiffeur = gson.fromJson(json, type);
            } else {
                Log.e("API", "Erreur HTTP : " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }

        return CoupeCoiffeur;
    }

    //api qui reprend toutes les photos
    public List<Photo> getPhoto(){
        List<Photo> photo = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/photos")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                Gson gson = new Gson();  // gson arrive a créer un classe a partir de json
                Type type = new TypeToken<List<Photo>>(){}.getType();
                photo = gson.fromJson(json, type);
            } else {
                Log.e("API", "Erreur HTTP : " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }

        return photo;
    }
    /*public List<Client> getClients(){
        List<Client> clients = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.1.216:5000/client")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                // Désérialisation avec Gson
                Gson gson = new Gson();
                Type type = new TypeToken<List<Client>>(){}.getType();
                clients = gson.fromJson(json, type);
            } else {
                Log.e("API", "Erreur HTTP : " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }

        return clients;
    }*/


    //hard coded
    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();

        // Create and add first test client
        Client client1 = new Client();
        client1.setId(1);
        client1.setName("John Doe");
        client1.setEmail("john@example.com");
        client1.setAddress("123 Main St");
        client1.setCity("Montreal");
        client1.setProvince("Quebec");
        client1.setPostal_code("H1H 1H1");
        client1.setPhone("password123"); //mot de passe pour l'instant, probleme avec hash
        clients.add(client1);

        Client client2 = new Client();
        client2.setId(2);
        client2.setName("Jane Smith");
        client2.setEmail("jane@example.com");
        client2.setAddress("456 Oak Ave");
        client2.setCity("Toronto");
        client2.setProvince("Ontario");
        client2.setPostal_code("M1M 1M1");
        client2.setPhone("test123"); //phone est le mot de passe pour l'instant, probleme avec hash
        clients.add(client2);

        return clients;
    }
    public boolean setClient(Client client) {
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new Gson();
        String jsonBody = gson.toJson(client);

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/client") // Update if your endpoint is different
                .post(okhttp3.RequestBody.create(jsonBody, okhttp3.MediaType.parse("application/json")))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Log.d("API", "Client ajouté avec succès");
                return true;
            } else {
                Log.e("API", "Erreur HTTP lors de l'ajout du client : " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau lors de l'ajout du client : " + e.getMessage());
        }

        return false;
    }

    //api qui reprend tous les rendez-vous
    public List<RendezVous> getRendezVous() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        int clientId = Client.CLIENT_COURANT.getId();

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/rendezvous")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();
                Gson gson = new Gson();  // gson arrive a créer un classe a partir de json
                Type type = new TypeToken<List<RendezVous>>(){}.getType();
                rendezVousList = gson.fromJson(json, type);

                // Filtrer pour n'obtenir que les rendez-vous du client connecté
                List<RendezVous> filteredList = new ArrayList<>();
                for (RendezVous rdv : rendezVousList) {
                    if (rdv.getId_user() == clientId) {
                        filteredList.add(rdv);
                    }
                }

                return filteredList;
            } else {
                Log.e("API", "Erreur HTTP : " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }

        return rendezVousList;
    }

    //api pour créer un rendezVous
    public boolean creerRendezVous(int idCreneau, int idClient, String typeService, double prix, int idCoiffeur) {
        OkHttpClient client = new OkHttpClient();

        // Créer l'objet JSON pour la requête
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_creneau", idCreneau);
            jsonObject.put("id_user", idClient);
            jsonObject.put("confirmation_envoye", false);
            jsonObject.put("rappel_envoye", false);
            jsonObject.put("etatRendezVous", false);
            // On n'a pas besoin d'envoyer typeService et prix, car ils sont gérés côté serveur
        } catch (JSONException e) {
            Log.e("API", "Erreur JSON : " + e.getMessage());
            return false;
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/rendezvous")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
            return false;
        }
    }

    //api pour annuler un rendez-vous
    public boolean annulerRendezVous(int idRendezVous) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/rendezvous/" + idRendezVous)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
            return false;
        }
    }

    //api pour ajouter un avis
    public boolean ajouterAvis(int idRendezVous, String titre, String commentaire, int note) {
        OkHttpClient client = new OkHttpClient();

        // Créer l'objet JSON pour la requête
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_rendezvous", idRendezVous);
            jsonObject.put("titre", titre);
            jsonObject.put("commentaire", commentaire);
            jsonObject.put("note_sur_5", note);
            jsonObject.put("chemin_photo", ""); // On n'utilise pas de photo pour simplifier
        } catch (JSONException e) {
            Log.e("API", "Erreur JSON : " + e.getMessage());
            return false;
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("http://192.168.76.55:5000/avis")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
            return false;
        }
    }

}
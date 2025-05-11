package com.example.barbersync;

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

public class Api {

    public List<Coiffeur> getCoiffeurs(){
        List<Coiffeur> coiffeurs = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.11.212:5000/coiffeurs")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                // Désérialisation avec Gson
                Gson gson = new Gson();
                Type type = new TypeToken<List<Coiffeur>>(){}.getType();
                coiffeurs = gson.fromJson(json, type);
            } else {
                Log.e("API", "Erreur HTTP : " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }

        return coiffeurs;
    }
    public List<Coupes> getCoupes(){
        List<Coupes> Coupes = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.11.212:5000/coupes")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                // Désérialisation avec Gson
                Gson gson = new Gson();
                Type type = new TypeToken<List<Coupes>>(){}.getType();
                Coupes = gson.fromJson(json, type);
            } else {
                Log.e("API", "Erreur HTTP : " + response.code());
            }
        } catch (IOException e) {
            Log.e("API", "Erreur réseau : " + e.getMessage());
        }

        return Coupes;
    }
    public List<Creneau> getCreneau(){
        List<Creneau> Creneau = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.11.212:5000/creneau")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                // Désérialisation avec Gson
                Gson gson = new Gson();
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
    public List<CreneauCoiffeur> getCreneauCoiffeur(){
        List<CreneauCoiffeur> CreneauCoiffeur = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.11.212:5000/creneauCoiffeur")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                // Désérialisation avec Gson
                Gson gson = new Gson();
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
    public List<CoupeCoiffeur> getCoupeCoiffeurs(){
        List<CoupeCoiffeur> CoupeCoiffeur = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.11.212:5000/coupeCoiffeur")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                // Désérialisation avec Gson
                Gson gson = new Gson();
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

    public List<Photo> getPhoto(){
        List<Photo> photo = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.11.212:5000/photos")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String json = response.body().string();

                // Désérialisation avec Gson
                Gson gson = new Gson();
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
                .url("http://192.168.11.212:5000/client")
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
        client2.setPhone("test123"); //mot de passe pour l'instant, probleme avec hash
        clients.add(client2);

        return clients;
    }
}
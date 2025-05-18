package com.example.barbersync;

import java.io.Serializable;

public class Photo implements Serializable {
    private int id;
    private int user_id;
    private String nomFichierImage;
    private String description;

    public Photo(int id, int user_id, String nomFichierImage, String description) {
        this.id = id;
        this.user_id = user_id;
        this.nomFichierImage = nomFichierImage;
        this.description = description;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getNomFichierImage() {
        return nomFichierImage;
    }

    public String getDescription() {
        return description;
    }

    // Setters (si besoin)
    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setNomFichierImage(String nomFichierImage) {
        this.nomFichierImage = nomFichierImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

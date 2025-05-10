package com.example.barbersync;

public class Photo {
    private int id;
    private int user_id;
    private String nomFichierImage;
    private String description;

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

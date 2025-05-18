package com.example.barbersync;

import java.io.Serializable;

public class CoupeCoiffeur implements Serializable {
    private int user_id;
    private int coupe_id;
    private double prix;
    private String nom;

    public CoupeCoiffeur(int coiffeur, int coupe, double prix) {
        this.user_id = coiffeur;
        this.coupe_id = coupe;
        this.prix = prix;

    }

    public CoupeCoiffeur(double prix, String nom) {
        this.prix = prix;
        this.nom = nom;

    }

    public int getCoiffeur() {
        return user_id;
    }

    public void setCoiffeur(int coiffeur) {
        this.user_id = coiffeur;
    }

    public int getCoupe() {
        return coupe_id;
    }

    public void setCoupe(int coupe) {
        this.coupe_id = coupe;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }
}

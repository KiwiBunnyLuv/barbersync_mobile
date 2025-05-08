package com.example.barbersync;

import java.util.ArrayList;
import java.util.List;

public class Coupes {
    private int id;
    private String coupe;
    private List<CoupeCoiffeur> coiffeurs;

    // Constructeur complet
    public Coupes(int id, String nom) {
        this.id = id;
        this.coupe = coupe;
        this.coiffeurs = new ArrayList<>();
    }
    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return coupe;
    }
    public void setNom(String nom) {
        this.coupe = nom;
    }
    public List<CoupeCoiffeur> getCoiffeurs() {
        return coiffeurs;
    }
    public void setCoiffeurs(List<CoupeCoiffeur> coiffeurs) {
        this.coiffeurs = coiffeurs;
    }
    @Override
    public String toString() {
        return "com.example.barbersync.Coupes{" +
                "id=" + id +
                ", nom='" + coupe + '\'' +
                ", coiffeurs=" + coiffeurs +
                '}';

    }
}

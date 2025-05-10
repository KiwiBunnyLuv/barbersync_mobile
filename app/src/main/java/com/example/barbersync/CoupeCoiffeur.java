package com.example.barbersync;

public class CoupeCoiffeur {
    private int coiffeur;
    private int coupe;
    private double prix;

    public CoupeCoiffeur(int coiffeur, int coupe, double prix) {
        this.coiffeur = coiffeur;
        this.coupe = coupe;
        this.prix = prix;
    }
    public int getCoiffeur() {
        return coiffeur;
    }

    public void setCoiffeur(int coiffeur) {
        this.coiffeur = coiffeur;
    }

    public int getCoupe() {
        return coupe;
    }

    public void setCoupe(int coupe) {
        this.coupe = coupe;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "com.example.barbersync.CoupeCoiffeur{" +
                "coiffeur=" + coiffeur.getName() +
                ", coupe=" + coupe.getNom() +
                ", tarif=" + prix +
                '}';
    }
}

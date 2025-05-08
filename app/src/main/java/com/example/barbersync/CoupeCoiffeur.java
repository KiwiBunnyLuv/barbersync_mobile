package com.example.barbersync;

public class CoupeCoiffeur {
    private Coiffeur coiffeur;
    private Coupes coupe;
    private double prix;

    public CoupeCoiffeur(Coiffeur coiffeur, Coupes coupe, double prix) {
        this.coiffeur = coiffeur;
        this.coupe = coupe;
        this.prix = prix;
    }
    public Coiffeur getCoiffeur() {
        return coiffeur;
    }

    public void setCoiffeur(Coiffeur coiffeur) {
        this.coiffeur = coiffeur;
    }

    public Coupes getCoupe() {
        return coupe;
    }

    public void setCoupe(Coupes coupe) {
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

package com.example.barbersync;

public class CreneauCoiffeur {
    private int coiffeur;
    private int creneau;
    private int dispo;
    private int reserve;

    public CreneauCoiffeur(int coiffeur, int creneau, int dispo, int reserve ) {
        this.coiffeur = coiffeur;
        this.creneau = creneau;
        this.dispo = dispo;
        this.reserve = reserve;
    }

    public int getCoiffeurs() {
        return coiffeur;
    }

    public void setCoiffeur(int coiffeur) {
        this.coiffeur = coiffeur;
    }

    public int getCreneau() {
        return creneau;
    }

    public void setCreneau(int creneau) {
        this.creneau = creneau;
    }

    public int getDispo() {
        return dispo;
    }
    public void setDispo(int estDispo) {
        this.dispo = estDispo;
    }
    public int getReserve() {
        return reserve;
    }
    public void setReserve(int estReserve) {
        this.reserve = estReserve;
    }


}

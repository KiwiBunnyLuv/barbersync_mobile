package com.example.barbersync;

public class CreneauCoiffeur {
    private int id_coiffeur;
    private int id_creneau;
    private int dispo;
    private int reserve;

    public CreneauCoiffeur(int coiffeur, int creneau, int dispo, int reserve ) {
        this.id_coiffeur = coiffeur;
        this.id_creneau = creneau;
        this.dispo = dispo;
        this.reserve = reserve;
    }

    public int getCoiffeurs() {
        return id_coiffeur;
    }

    public void setCoiffeur(int coiffeur) {
        this.id_coiffeur = coiffeur;
    }

    public int getCreneau() {
        return id_creneau;
    }

    public void setCreneau(int creneau) {
        this.id_creneau = creneau;
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

package com.example.barbersync;

public class CreneauCoiffeur {
    private int coiffeur;
    private int creneau;
    private Boolean dispo;
    private Boolean reserve;

    public CreneauCoiffeur(int coiffeur, int creneau, Boolean dispo, Boolean reserve ) {
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

    public Boolean getDispo() {
        return dispo;
    }
    public void setDispo(Boolean estDispo) {
        this.dispo = estDispo;
    }
    public Boolean getReserve() {
        return reserve;
    }
    public void setReserve(Boolean estReserve) {
        this.reserve = estReserve;
    }

    @Override
    public String toString() {
        return "com.example.barbersync.CreneauCoiffeur{" +
                "coiffeur=" + coiffeur.getName() +
                ", creneau=" + creneau.getDate() +
                '}';
    }

}

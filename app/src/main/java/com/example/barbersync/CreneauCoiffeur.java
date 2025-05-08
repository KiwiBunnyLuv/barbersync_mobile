package com.example.barbersync;

public class CreneauCoiffeur {
    private Coiffeur coiffeur;
    private Creneau creneau;
    private Boolean dispo;
    private Boolean reserve;

    public CreneauCoiffeur(Coiffeur coiffeur, Creneau creneau, Boolean dispo, Boolean reserve ) {
        this.coiffeur = coiffeur;
        this.creneau = creneau;
        this.dispo = dispo;
        this.reserve = reserve;
    }

    public Coiffeur getCoiffeurs() {
        return coiffeur;
    }

    public void setCoiffeur(Coiffeur coiffeur) {
        this.coiffeur = coiffeur;
    }

    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
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

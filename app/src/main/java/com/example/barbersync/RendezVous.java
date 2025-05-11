package com.example.barbersync;

import java.io.Serializable;

public class RendezVous implements Serializable {
    private int id_rendezvous;
    private boolean confirmation_envoye;
    private boolean rappel_envoye;
    private Creneau creneau;
    private boolean etatRendezVous;
    private int id_user;
    private String nomCoiffeur;
    private String typeService;
    private double prix;

    // Constructeur vide
    public RendezVous() {
    }

    // Constructeur complet
    public RendezVous(int id_rendezvous, boolean confirmation_envoye, boolean rappel_envoye,
                      Creneau creneau, boolean etatRendezVous, int id_user,
                      String nomCoiffeur, String typeService, double prix) {
        this.id_rendezvous = id_rendezvous;
        this.confirmation_envoye = confirmation_envoye;
        this.rappel_envoye = rappel_envoye;
        this.creneau = creneau;
        this.etatRendezVous = etatRendezVous;
        this.id_user = id_user;
        this.nomCoiffeur = nomCoiffeur;
        this.typeService = typeService;
        this.prix = prix;
    }

    // Getters et Setters
    public int getId_rendezvous() {
        return id_rendezvous;
    }

    public void setId_rendezvous(int id_rendezvous) {
        this.id_rendezvous = id_rendezvous;
    }

    public boolean isConfirmation_envoye() {
        return confirmation_envoye;
    }

    public void setConfirmation_envoye(boolean confirmation_envoye) {
        this.confirmation_envoye = confirmation_envoye;
    }

    public boolean isRappel_envoye() {
        return rappel_envoye;
    }

    public void setRappel_envoye(boolean rappel_envoye) {
        this.rappel_envoye = rappel_envoye;
    }

    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }

    public boolean isEtatRendezVous() {
        return etatRendezVous;
    }

    public void setEtatRendezVous(boolean etatRendezVous) {
        this.etatRendezVous = etatRendezVous;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNomCoiffeur() {
        return nomCoiffeur;
    }

    public void setNomCoiffeur(String nomCoiffeur) {
        this.nomCoiffeur = nomCoiffeur;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id_rendezvous=" + id_rendezvous +
                ", date=" + (creneau != null ? creneau.getDate() : "n/a") +
                ", heure=" + (creneau != null ? creneau.getHeureDebut() : "n/a") +
                ", nomCoiffeur='" + nomCoiffeur + '\'' +
                ", service='" + typeService + '\'' +
                ", prix=" + prix +
                '}';
    }
}
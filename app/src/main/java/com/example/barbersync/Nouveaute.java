package com.example.barbersync;
import java.util.Date;

public class Nouveaute {
    private int id_nouveaute;
    private String nom;
    private String description;
    private String date_debut;
    private String date_fin;
    private Boolean isActive;
    private String type;

    public Nouveaute(int id_nouveaute,String nom, String description, String dateDebut, String dateFin, Boolean isActive, String type) {
        this.id_nouveaute = id_nouveaute;
        this.nom = nom;
        this.description = description;
        this.date_debut = dateDebut;
        this.date_fin = dateFin;
        this.isActive = isActive;
        this.type = type;
    }
    public String getId() {
        return String.valueOf(this.id_nouveaute);
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDateDebut() {
        return date_debut;
    }
    public void setDateDebut(String dateDebut) {
        this.date_debut = dateDebut;
    }
    public String getDateFin() {
        return date_fin;
    }
    public void setDateFin(String dateFin) {
        this.date_fin = dateFin;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}

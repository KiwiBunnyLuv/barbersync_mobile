package com.example.barbersync;
import java.util.Date;

public class Nouveaute {
    private String titre;
    private String description;
    private Date date_debut;
    private Date date_fin;
    private Boolean isActive;
    private String type;

    public Nouveaute(String titre, String description, Date dateDebut, Date dateFin, Boolean isActive, String type) {
        this.titre = titre;
        this.description = description;
        this.date_debut = dateDebut;
        this.date_fin = dateFin;
        this.isActive = isActive;
        this.type = type;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDateDebut() {
        return date_debut;
    }
    public void setDateDebut(Date dateDebut) {
        this.date_debut = dateDebut;
    }
    public Date getDateFin() {
        return date_fin;
    }
    public void setDateFin(Date dateFin) {
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

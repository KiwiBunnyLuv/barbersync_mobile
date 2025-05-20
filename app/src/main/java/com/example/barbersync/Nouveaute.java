/****************************************
 Fichier : Nouveaute.java
 Auteur : Nicolas Beaudoin
 Fonctionnalité : Représente une nouveauté dans l'application
 Date : 2025-05-07

 Vérification :
 2025-05-20     Yassine Abide        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-20     Nicolas Beaudoin           Ajout de commentaires et javadoc
 =========================================================
 ****************************************/

package com.example.barbersync;
import java.util.Date;
/**
 * Classe représentant une nouveauté avec ses informations principales.
 */
public class Nouveaute {
    private int id_nouveaute;
    private String nom;
    private String description;
    private String date_debut;
    private String date_fin;
    private Boolean isActive;
    private String type;
    /**
     * Constructeur complet.
     * @param id_nouveaute Identifiant de la nouveauté.
     * @param nom Nom de la nouveauté.
     * @param description Description de la nouveauté.
     * @param dateDebut Date de début de la nouveauté.
     * @param dateFin Date de fin de la nouveauté.
     * @param isActive Statut actif ou non de la nouveauté.
     * @param type Type de la nouveauté.
     */
    public Nouveaute(int id_nouveaute,String nom, String description, String dateDebut, String dateFin, Boolean isActive, String type) {
        this.id_nouveaute = id_nouveaute;
        this.nom = nom;
        this.description = description;
        this.date_debut = dateDebut;
        this.date_fin = dateFin;
        this.isActive = isActive;
        this.type = type;
    }
    /** @return l'identifiant de la nouveauté */
    public String getId() {
        return String.valueOf(this.id_nouveaute);
    }
    /** @return le nom de la nouveauté */
    public String getNom() {
        return nom;
    }
    /** @param nom le nom à définir */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /** @return la description de la nouveauté */
    public String getDescription() {
        return description;
    }
    /** @param description la description à définir */
    public void setDescription(String description) {
        this.description = description;
    }
    /** @return la date de début de la nouveauté */
    public String getDateDebut() {
        return date_debut;
    }
    /** @param dateDebut la date de début à définir */
    public void setDateDebut(String dateDebut) {
        this.date_debut = dateDebut;
    }
    /** @return la date de fin de la nouveauté */
    public String getDateFin() {
        return date_fin;
    }
    /** @param dateFin la date de fin à définir */
    public void setDateFin(String dateFin) {
        this.date_fin = dateFin;
    }
    /** @return le statut actif ou non de la nouveauté */
    public Boolean getIsActive() {
        return isActive;
    }
    /** @param isActive le statut actif à définir */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    /** @return le type de la nouveauté */
    public String getType() {
        return type;
    }
    /** @param type le type à définir */
    public void setType(String type) {
        this.type = type;
    }
}

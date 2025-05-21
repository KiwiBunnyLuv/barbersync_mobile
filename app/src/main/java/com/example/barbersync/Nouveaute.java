/****************************************
 * Fichier : Nouveaute.java
 * Auteur : Nicolas Beaudoin
 * Fonctionnalité : Représente une nouveauté dans l'application
 * Date : 2025-05-19
 *
 * Vérification :
 * 2025-05-20     Yassine Abide        Approuvé
 * =========================================================
 * Historique de modifications :
 * 2025-05-20     Nicolas Beaudoin           Ajout de commentaires
 * =========================================================
 ****************************************/

package com.example.barbersync;

import com.google.gson.annotations.SerializedName;

/**
 * Classe représentant une nouveauté dans l'application qui étend BaseNotification.
 */
public class Nouveaute extends BaseNotification {
    @SerializedName("id_nouveaute")
    private int id;
    private String nom;
    private String description;
    private String dateDebut;
    private String dateFin;
    @SerializedName("isActive")
    private boolean isActive;
    private String type;

    /**
     * Constructeur de la classe Nouveaute.
     * @param id Identifiant de la nouveauté.
     * @param nom Titre de la nouveauté.
     * @param description Description de la nouveauté.
     * @param dateDebut Date de début de la nouveauté.
     * @param dateFin Date de fin de la nouveauté.
     * @param isActive État d'activation de la nouveauté.
     * @param type Type de nouveauté.
     */
    public Nouveaute(int id, String nom, String description, String dateDebut, String dateFin, boolean isActive, String type, boolean isRead) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.isActive = isActive;
        this.type = type;
        setRead(isRead);

    }
    // Implémentation des méthodes abstraites de BaseNotification
    @Override
    public String getTitle() {
        return nom;
    }

    @Override
    public String getMessage() {
        return description;
    }

    // Getters pour les autres propriétés
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setType(String type) {
        this.type = type;
    }
}
/****************************************
 Fichier : Avis.java
 Auteur : Ramin Amiri
 Fonctionnalité : MOBAVIS - Classe modèle représentant un avis client
 Date : 2025-05-30

 Vérification :
 2025-05-30     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 =========================================================
 ****************************************/

package com.example.barbersync;

import java.io.Serializable;

/**
 * Classe représentant un avis laissé par un client pour un coiffeur
 */
public class Avis implements Serializable {

    private int idEvaluation;
    private int idRendezVous;
    private String titre;
    private String commentaire;
    private int noteSur5;
    private String username;
    private String cheminPhoto;
    private String createdAt;
    private String reponse;

    /**
     * Constructeur minimal pour l'affichage des avis
     * @param username Nom d'utilisateur qui a laissé l'avis
     * @param noteSur5 Note sur 5 étoiles
     * @param commentaire Contenu de l'avis
     */
    public Avis(String username, int noteSur5, String commentaire) {
        this.username = username;
        this.noteSur5 = noteSur5;
        this.commentaire = commentaire;
        this.titre = ""; // Titre par défaut vide
        this.cheminPhoto = ""; // Chemin photo par défaut vide
    }

    /**
     * Constructeur complet
     * @param idEvaluation ID de l'évaluation
     * @param idRendezVous ID du rendez-vous associé
     * @param titre Titre de l'avis
     * @param commentaire Contenu de l'avis
     * @param noteSur5 Note sur 5 étoiles
     * @param username Nom d'utilisateur qui a laissé l'avis
     * @param cheminPhoto Chemin vers la photo associée (peut être vide)
     * @param createdAt Date de création de l'avis
     * @param reponse Réponse du coiffeur (peut être null)
     */
    public Avis(int idEvaluation, int idRendezVous, String titre, String commentaire,
                int noteSur5, String username, String cheminPhoto, String createdAt, String reponse) {
        this.idEvaluation = idEvaluation;
        this.idRendezVous = idRendezVous;
        this.titre = titre;
        this.commentaire = commentaire;
        this.noteSur5 = noteSur5;
        this.username = username != null && !username.isEmpty() ? username : "Client";
        this.cheminPhoto = cheminPhoto;
        this.createdAt = createdAt;
        this.reponse = reponse;
    }

    /**
     * Constructeur à partir des données JSON
     * @param idEvaluation ID de l'évaluation
     * @param idRendezVous ID du rendez-vous associé
     * @param titre Titre de l'avis
     * @param commentaire Contenu de l'avis
     * @param noteSur5 Note sur 5 étoiles
     * @param cheminPhoto Chemin vers la photo associée (peut être vide)
     * @param createdAt Date de création de l'avis
     * @param reponse Réponse du coiffeur (peut être null)
     */
    public Avis(int idEvaluation, int idRendezVous, String titre, String commentaire,
                int noteSur5, String cheminPhoto, String createdAt, String reponse) {
        this.idEvaluation = idEvaluation;
        this.idRendezVous = idRendezVous;
        this.titre = titre;
        this.commentaire = commentaire;
        this.noteSur5 = noteSur5;
        this.username = "Client"; // Valeur par défaut
        this.cheminPhoto = cheminPhoto;
        this.createdAt = createdAt;
        this.reponse = reponse;
    }

    // Getters et Setters

    public int getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(int idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public int getIdRendezVous() {
        return idRendezVous;
    }

    public void setIdRendezVous(int idRendezVous) {
        this.idRendezVous = idRendezVous;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getNote() {
        return noteSur5;
    }

    public void setNote(int noteSur5) {
        this.noteSur5 = noteSur5;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCheminPhoto() {
        return cheminPhoto;
    }

    public void setCheminPhoto(String cheminPhoto) {
        this.cheminPhoto = cheminPhoto;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    @Override
    public String toString() {
        return "Avis{" +
                "idEvaluation=" + idEvaluation +
                ", titre='" + titre + '\'' +
                ", note=" + noteSur5 +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
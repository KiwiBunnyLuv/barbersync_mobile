/****************************************
 Fichier : Photo.java
 Auteur : samit sabah adelyar
 Fonctionnalité : aucune, mais utile pour MOBSER1 et MOBSER2 (lister coiffeur et afficher détail coiffeur)
 Date : 2025-05-10

 Vérification :
 2025-05-12     Nicolas Beaudoin        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-20     Samit Adelyar           ajout de commentaires et javadoc
 =========================================================
 ****************************************/

package com.example.barbersync;

import java.io.Serializable;

import java.io.Serializable;

/**
 * Représente une photo associée à un coiffeur,
 * contenant un identifiant, le nom du fichier et une description.
 */
public class Photo implements Serializable {
    private int id;
    private int user_id;
    private String nomFichierImage;
    private String description;

    /**
     * Constructeur complet.
     * @param id identifiant de la photo
     * @param user_id identifiant du coiffeur associé
     * @param nomFichierImage nom du fichier image
     * @param description description de la photo
     */
    public Photo(int id, int user_id, String nomFichierImage, String description) {
        this.id = id;
        this.user_id = user_id;
        this.nomFichierImage = nomFichierImage;
        this.description = description;
    }

    /** @return l'identifiant de la photo */
    public int getId() {
        return id;
    }

    /** @return l'identifiant du coiffeur associé */
    public int getUser_id() {
        return user_id;
    }

    /** @return le nom du fichier image */
    public String getNomFichierImage() {
        return nomFichierImage;
    }

    /** @return la description de la photo */
    public String getDescription() {
        return description;
    }

    /** @param id l'identifiant à définir */
    public void setId(int id) {
        this.id = id;
    }

    /** @param user_id l'identifiant du coiffeur à définir */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /** @param nomFichierImage le nom du fichier image à définir */
    public void setNomFichierImage(String nomFichierImage) {
        this.nomFichierImage = nomFichierImage;
    }

    /** @param description la description à définir */
    public void setDescription(String description) {
        this.description = description;
    }
}

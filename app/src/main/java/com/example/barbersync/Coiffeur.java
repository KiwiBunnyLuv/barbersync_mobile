/****************************************
 Fichier : coiffeur.java
 Auteur : samit sabah adelyar
 Fonctionnalité : aucune, mais utile pour MOBSER1 et MOBSER2
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
import java.util.ArrayList;
import java.util.List;


/**
 * Représente un coiffeur avec son identifiant, nom, biographie,
 * ses coupes proposées, créneaux disponibles et photos associées.
 */
public class Coiffeur implements Serializable {
    private int id;
    private String nom;
    private String biographie;
    private List<CoupeCoiffeur> coupes;
    private List<CreneauCoiffeur> creneaux;
    private List<Photo> photos;

    /**
     * Constructeur par défaut.
     */
    public Coiffeur() {
        this.coupes = new ArrayList<>();
        this.creneaux = new ArrayList<>();
        this.photos = new ArrayList<>();
    }

    /**
     * Constructeur complet.
     * @param id Identifiant du coiffeur
     * @param name Nom du coiffeur
     * @param biographie Biographie du coiffeur
     */
    public Coiffeur(int id , String name, String biographie) {
        this.id = id;
        this.nom = name;
        this.biographie = biographie;
        this.coupes = new ArrayList<>();
        this.creneaux = new ArrayList<>();
        this.photos = new ArrayList<>();
    }

    /** @return l'identifiant du coiffeur */
    public int getId() {
        return id;
    }

    /** @param id l'identifiant à définir */
    public void setId(int id) {
        this.id = id;
    }

    /** @return le nom du coiffeur */
    public String getName() {
        return this.nom;
    }

    /** @param name le nom à définir */
    public void setName(String name) {
        this.nom = name;
    }

    /** @return la biographie du coiffeur */
    public String getBiographie() {
        return this.biographie;
    }

    /** @param biographie la biographie à définir */
    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    /** @return la liste des coupes proposées */
    public List<CoupeCoiffeur> getCoupes() {
        return coupes;
    }

    /** @param coupes la liste des coupes à définir */
    public void setCoupes(List<CoupeCoiffeur> coupes) {
        this.coupes = coupes;
    }

    /** @return la liste des créneaux disponibles */
    public List<CreneauCoiffeur> getCreneaux() {
        return creneaux;
    }

    /** @param creneaux la liste des créneaux à définir */
    public void setCreneaux(List<CreneauCoiffeur> creneaux) {
        this.creneaux = creneaux;
    }

    /** @return la liste des photos associées */
    public List<Photo> getPhotos() {
        return this.photos;
    }

    /** @param p la liste des photos à définir */
    public void setPhotos(List<Photo> p) {
        this.photos = p;
    }

    /**
     * Ajoute une coupe avec un tarif associé au coiffeur.
     * @param coupe la coupe à associer
     * @param tarif le tarif de cette coupe
     */
    public void addCoupe(Coupes coupe, double tarif) {
        CoupeCoiffeur relation = new CoupeCoiffeur(id, coupe.getId(), tarif);
        this.coupes.add(relation);
        coupe.getCoiffeurs().add(relation);
    }

    /**
     * Ajoute un créneau à la liste des créneaux disponibles.
     * @param creneau le créneau à ajouter
     */
    public void addCreneau(CreneauCoiffeur creneau) {
        this.creneaux.add(creneau);
    }

    @Override
    public String toString() {
        return "com.example.barbersync.Coiffeur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", biographie='" + biographie + '\'' +
                '}';
    }
}

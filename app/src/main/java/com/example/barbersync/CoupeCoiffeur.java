/****************************************
 Fichier : CoupeCoiffeur.java
 Auteur : samit sabah adelyar
 Fonctionnalité : aucune, mais utile pour MOBSER1 et MOBSER2  (lister coiffeur et afficher détail coiffeur)
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
 * Représente la relation entre un coiffeur et une coupe,
 * avec un tarif associé et éventuellement un nom de coupe.
 */
public class CoupeCoiffeur implements Serializable {
    private int user_id;
    private int coupe_id;
    private double prix;
    private String nom;

    /**
     * Constructeur avec identifiants du coiffeur et de la coupe, et un tarif.
     * @param coiffeur identifiant du coiffeur
     * @param coupe identifiant de la coupe
     * @param prix prix associé à cette coupe pour ce coiffeur
     */
    public CoupeCoiffeur(int coiffeur, int coupe, double prix) {
        this.user_id = coiffeur;
        this.coupe_id = coupe;
        this.prix = prix;
    }

    /**
     * Constructeur avec prix et nom (sans identifiants).
     * @param prix prix de la coupe
     * @param nom nom de la coupe
     */
    public CoupeCoiffeur(double prix, String nom) {
        this.prix = prix;
        this.nom = nom;
    }

    /** @return l'identifiant du coiffeur */
    public int getCoiffeur() {
        return user_id;
    }

    /** @param coiffeur identifiant du coiffeur à définir */
    public void setCoiffeur(int coiffeur) {
        this.user_id = coiffeur;
    }

    /** @return l'identifiant de la coupe */
    public int getCoupe() {
        return coupe_id;
    }

    /** @param coupe identifiant de la coupe à définir */
    public void setCoupe(int coupe) {
        this.coupe_id = coupe;
    }

    /** @return le prix de la coupe */
    public double getPrix() {
        return prix;
    }

    /** @param prix le prix à définir */
    public void setPrix(double prix) {
        this.prix = prix;
    }

    /** @return le nom de la coupe */
    public String getNom() {
        return nom;
    }

    /** @param nom le nom de la coupe à définir */
    public void setNom(String nom){
        this.nom = nom;
    }
}

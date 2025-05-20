/****************************************
 Fichier : coupe.java
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
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un type de coupe de cheveux, avec un identifiant, un nom
 * et une liste de coiffeurs qui proposent cette coupe avec leur tarif.
 */
public class Coupes implements Serializable {
    private int id;
    private String coupe;
    private List<CoupeCoiffeur> coiffeurs;

    /**
     * Constructeur complet.
     * @param id identifiant de la coupe
     * @param coupe nom de la coupe
     */
    public Coupes(int id, String coupe) {
        this.id = id;
        this.coupe = coupe;
        this.coiffeurs = new ArrayList<>();
    }

    /** @return l'identifiant de la coupe */
    public int getId() {
        return id;
    }

    /** @param id l'identifiant à définir */
    public void setId(int id) {
        this.id = id;
    }

    /** @return le nom de la coupe */
    public String getNom() {
        return this.coupe;
    }

    /** @param nom le nom de la coupe à définir */
    public void setNom(String nom) {
        this.coupe = nom;
    }

    /** @return la liste des coiffeurs associés à cette coupe */
    public List<CoupeCoiffeur> getCoiffeurs() {
        return coiffeurs;
    }

    /** @param coiffeurs la liste des coiffeurs à associer à cette coupe */
    public void setCoiffeurs(List<CoupeCoiffeur> coiffeurs) {
        this.coiffeurs = coiffeurs;
    }

    @Override
    public String toString() {
        return "com.example.barbersync.Coupes{" +
                "id=" + id +
                ", nom='" + coupe + '\'' +
                ", coiffeurs=" + coiffeurs +
                '}';
    }
}
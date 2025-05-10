package com.example.barbersync;

import java.util.ArrayList;
import java.util.List;

public class Coiffeur {
    private int id;
    private String name;
    private String biographie;
    private List<CoupeCoiffeur> coupes;
    private List<CreneauCoiffeur> creneaux;

    // Constructeur par défaut
    public Coiffeur() {
        this.coupes = new ArrayList<>();
        this.creneaux = new ArrayList<>();
    }

    // Constructeur complet
    public Coiffeur(int id, String name, String biographie) {
        this.id = id;
        this.name = name;
        this.biographie = biographie;
        this.coupes = new ArrayList<>();
        this.creneaux = new ArrayList<>();
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public List<CoupeCoiffeur> getCoupes() {
        return coupes;
    }

    public void setCoupes(List<CoupeCoiffeur> coupes) {
        this.coupes = coupes;
    }

    public List<CreneauCoiffeur> getCreneaux() {
        return creneaux;
    }

    public void setCreneaux(List<CreneauCoiffeur> creneaux) {
        this.creneaux = creneaux;
    }

    // Ajouter une coupe avec tarif
    public void addCoupe(Coupes coupe, double tarif) {
        CoupeCoiffeur relation = new CoupeCoiffeur(id, coupe.getId(), tarif);
        this.coupes.add(relation);
        coupe.getCoiffeurs().add(relation);
    }

    // Ajouter un créneau
    public void addCreneau(CreneauCoiffeur creneau) {
        this.creneaux.add(creneau);
    }

    @Override
    public String toString() {
        return "com.example.barbersync.Coiffeur{" +
                "id=" + id +
                ", nom='" + name + '\'' +
                ", biographie='" + biographie + '\'' +
                '}';
    }

}

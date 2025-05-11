package com.example.barbersync;

import java.io.Serializable;
import java.time.LocalDate;

public class Creneau implements Serializable {
    private int id;
    private String heure_debut;
    private String heure_fin;
    private String date;

    // Constructeur
    public Creneau(int id, String heureDebut, String heureFin, String date) {
        this.id = id;
        this.heure_debut = heureDebut;
        this.heure_fin = heureFin;
        this.date = date;
    }

    // Méthodes d'accès (getters et setters)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeureDebut() {
        return heure_debut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heure_debut = heureDebut;
    }

    public String getHeureFin() {
        return heure_fin;
    }

    public void setHeureFin(String heureFin) {
        this.heure_fin = heureFin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Méthode pour afficher les détails du créneau
    @Override
    public String toString() {
        return "com.example.barbersync.Creneau [id=" + id + ", heureDebut=" + heure_debut + ", heureFin=" + heure_fin + ", date=" + date + "]";
    }
}

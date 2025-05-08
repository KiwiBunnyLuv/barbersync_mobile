package com.example.barbersync;

public class Creneau {
    private int id;
    private int heure_debut;
    private int heure_fin;
    private int date;

    // Constructeur
    public Creneau(int id, int heureDebut, int heureFin, int date) {
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

    public int getHeureDebut() {
        return heure_debut;
    }

    public void setHeureDebut(int heureDebut) {
        this.heure_debut = heureDebut;
    }

    public int getHeureFin() {
        return heure_fin;
    }

    public void setHeureFin(int heureFin) {
        this.heure_fin = heureFin;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    // Méthode pour afficher les détails du créneau
    @Override
    public String toString() {
        return "com.example.barbersync.Creneau [id=" + id + ", heureDebut=" + heure_debut + ", heureFin=" + heure_fin + ", date=" + date + "]";
    }
}

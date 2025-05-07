public class Creneau {
    private int id;
    private int heureDebut;
    private int heureFin;
    private int date;

    // Constructeur
    public Creneau(int id, int heureDebut, int heureFin, int date) {
        this.id = id;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
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
        return heureDebut;
    }

    public void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(int heureFin) {
        this.heureFin = heureFin;
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
        return "Creneau [id=" + id + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + ", date=" + date + "]";
    }
}

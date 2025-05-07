public class CoupeCoiffeur {
    private Coiffeur coiffeur;
    private Coupes coupe;
    private double tarif;

    public CoupeCoiffeur(Coiffeur coiffeur, Coupes coupe, double tarif) {
        this.coiffeur = coiffeur;
        this.coupe = coupe;
        this.tarif = tarif;
    }
    public Coiffeur getCoiffeur() {
        return coiffeur;
    }

    public void setCoiffeur(Coiffeur coiffeur) {
        this.coiffeur = coiffeur;
    }

    public Coupes getCoupe() {
        return coupe;
    }

    public void setCoupe(Coupes coupe) {
        this.coupe = coupe;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    @Override
    public String toString() {
        return "CoupeCoiffeur{" +
                "coiffeur=" + coiffeur.getNom() +
                ", coupe=" + coupe.getNom() +
                ", tarif=" + tarif +
                '}';
    }
}

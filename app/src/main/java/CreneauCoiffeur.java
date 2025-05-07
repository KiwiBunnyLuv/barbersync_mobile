public class CreneauCoiffeur {
    private Coiffeur coiffeur;
    private Creneau creneau;
    private Boolean estDispo;
    private Boolean estReserve;

    public CreneauCoiffeur(Coiffeur coiffeur, Creneau creneau, double tarif) {
        this.coiffeur = coiffeur;
        this.creneau = creneau;
    }

    public Coiffeur getCoiffeurs() {
        return coiffeur;
    }

    public void setCoiffeur(Coiffeur coiffeur) {
        this.coiffeur = coiffeur;
    }

    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }

    public Boolean getEstDispo() {
        return estDispo;
    }
    public void setEstDispo(Boolean estDispo) {
        this.estDispo = estDispo;
    }
    public Boolean getEstReserve() {
        return estReserve;
    }
    public void setEstReserve(Boolean estReserve) {
        this.estReserve = estReserve;
    }

    @Override
    public String toString() {
        return "CreneauCoiffeur{" +
                "coiffeur=" + coiffeur.getNom() +
                ", creneau=" + creneau.getDate() +
                '}';
    }

}

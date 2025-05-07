import java.util.ArrayList;
import java.util.List;

public class Coupes {
    private int id;
    private String nom;
    private List<CoupeCoiffeur> coiffeurs;

    // Constructeur complet
    public Coupes(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.coiffeurs = new ArrayList<>();
    }
    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public List<CoupeCoiffeur> getCoiffeurs() {
        return coiffeurs;
    }
    public void setCoiffeurs(List<CoupeCoiffeur> coiffeurs) {
        this.coiffeurs = coiffeurs;
    }
    @Override
    public String toString() {
        return "Coupes{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", coiffeurs=" + coiffeurs +
                '}';

    }
}

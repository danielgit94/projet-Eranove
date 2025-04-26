import java.util.HashMap;
import java.util.Map;

// Représente un élève avec son nom et ses notes
public class Eleve {
    private String nom;
    private Map<String, Double> notes;

    public Eleve(String nom) {
        this.nom = nom;
        this.notes = new HashMap<>();
    }

    public String getNom() { return nom; }

    public void ajouterNote(String matiere, double note) {
        notes.put(matiere, note);
    }

    public double getNote(String matiere) {
        return notes.getOrDefault(matiere, 0.0);
    }

    public Map<String, Double> getNotes() {
        return notes;
    }
}

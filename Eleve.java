import java.util.HashMap;
import java.util.Map;

public class Eleve {
    private static int compteur = 1; // Pour générer un ID unique automatiquement
    private final int id;
    private String nom;
    private String prenom;
    private String niveau; // Classe ou niveau d'étude (ex: 3e, 2nde, etc.)
    private Map<String, Double> notes;

    public Eleve(String nom, String prenom, String niveau) {
        this.id = compteur++;
        this.nom = nom;
        this.prenom = prenom;
        this.niveau = niveau;
        this.notes = new HashMap<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNiveau() {
        return niveau;
    }

    public Map<String, Double> getNotes() {
        return notes;
    }

    public double getNote(String matiere) {
        return notes.getOrDefault(matiere, 0.0);
    }

    // Ajouter ou modifier une note
    public void ajouterNote(String matiere, double note) {
        notes.put(matiere, note);
    }
}

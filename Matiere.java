// Représente une matière avec son nom et son coefficient
public class Matiere {
    private String nom;
    private int coefficient;

    public Matiere(String nom, int coefficient) {
        this.nom = nom;
        this.coefficient = coefficient;
    }

    public String getNom() { return nom; }
    public int getCoefficient() { return coefficient; }
}

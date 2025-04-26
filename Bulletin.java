import java.util.Map;

public class Bulletin {
    private static final Matiere[] MATIERES = {
        new Matiere("Mathematique", 4),
        new Matiere("Physique", 4),
        new Matiere("Francais", 2),
        new Matiere("Anglais", 1)
    };

    public static double calculerMoyenneGenerale(Eleve eleve) {
        double total = 0;
        int sommeCoefficients = 0;
        Map<String, Double> notes = eleve.getNotes();

        for (Matiere matiere : MATIERES) {
            double note = notes.getOrDefault(matiere.getNom(), 0.0);
            total += note * matiere.getCoefficient();
            sommeCoefficients += matiere.getCoefficient();
        }

        return total / sommeCoefficients;
    }

    public static void imprimerBulletin(Eleve eleve, double moyenne, int rang) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.printf("║  Bulletin de %-26s ║\n", eleve.getNom());
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ %-15s │ %-8s │ %-6s ║\n", "Matière", "Note", "Coef");
        System.out.println("╟────────────────────────────────────────╢");

        for (Matiere matiere : MATIERES) {
            double note = eleve.getNote(matiere.getNom());
            System.out.printf("║ %-15s │ %-8.2f │ %-6d ║\n", 
                matiere.getNom(), note, matiere.getCoefficient());
        }

        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Moyenne générale : %-18.2f ║\n", moyenne);
        System.out.printf("║ Rang : %-30d ║\n", rang);
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    public static Matiere[] getMatieres() {
        return MATIERES;
    }
}

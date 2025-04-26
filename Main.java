// Importations nécessaires
import java.util.*;
import java.io.*;

public class Main {
    static Map<String, Integer> nombreDeNotesParMatiere = new HashMap<>(); // Nombre de notes par matière

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Eleve> eleves = chargerDepuisCSV(); // Chargement des élèves au démarrage

        // Initialisation par défaut : 1 note par matière
        for (Matiere m : Bulletin.getMatieres()) {
            nombreDeNotesParMatiere.put(m.getNom(), 1);
        }

        int choix;
        do {
            // Menu principal
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Ajouter un élève");
            System.out.println("2. Afficher les bulletins");
            System.out.println("3. Rechercher un élève");
            System.out.println("4. Modifier les notes d’un élève");
            System.out.println("5. Supprimer un élève");
            System.out.println("6. Exporter en CSV");
            System.out.println("7. Définir le nombre de notes par matière");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            choix = scanner.nextInt();
            scanner.nextLine(); // Vider le buffer

            switch (choix) {
                case 1 -> ajouterEleve(scanner, eleves);
                case 2 -> afficherTousLesBulletins(eleves);
                case 3 -> rechercherEleve(scanner, eleves);
                case 4 -> modifierNotes(scanner, eleves);
                case 5 -> supprimerEleve(scanner, eleves);
                case 6 -> exporterCSV(eleves);
                case 7 -> definirNombreDeNotesParMatiere(scanner);
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide.");
            }
        } while (choix != 0);

        scanner.close();
    }

    // Définir le nombre de notes par matière
    public static void definirNombreDeNotesParMatiere(Scanner scanner) {
        System.out.println("\n=== Définir le nombre de notes par matière ===");
        for (Matiere matiere : Bulletin.getMatieres()) {
            System.out.print("Nombre de notes pour " + matiere.getNom() + " : ");
            int nombre = scanner.nextInt();
            scanner.nextLine();
            nombreDeNotesParMatiere.put(matiere.getNom(), nombre);
        }
    }

    // Fonction pour ajouter un élève
    public static void ajouterEleve(Scanner scanner, List<Eleve> eleves) {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Classe : ");
        String niveau = scanner.nextLine();

        Eleve eleve = new Eleve(nom, prenom, niveau);

        // Saisie des notes avec répétition selon le nombre défini
        for (Matiere matiere : Bulletin.getMatieres()) {
            int nombreNotes = nombreDeNotesParMatiere.getOrDefault(matiere.getNom(), 1);
            double total = 0;
            for (int i = 1; i <= nombreNotes; i++) {
                System.out.print("Note " + i + " en " + matiere.getNom() + " : ");
                total += scanner.nextDouble();
            }
            double moyenne = total / nombreNotes;
            eleve.ajouterNote(matiere.getNom(), moyenne);
        }
        scanner.nextLine();
        eleves.add(eleve);
        exporterCSV(eleves); // Sauvegarde automatique
        System.out.println("Élève ajouté avec succès.");
    }

    // Fonction pour afficher tous les bulletins
    public static void afficherTousLesBulletins(List<Eleve> eleves) {
        // Calcul du classement
        Map<Eleve, Double> moyennes = new HashMap<>();
        for (Eleve e : eleves) {
            moyennes.put(e, Bulletin.calculerMoyenneGenerale(e));
        }

        List<Map.Entry<Eleve, Double>> classement = new ArrayList<>(moyennes.entrySet());
        classement.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        for (int i = 0; i < classement.size(); i++) {
            Eleve e = classement.get(i).getKey();
            double moyenne = classement.get(i).getValue();
            Bulletin.imprimerBulletin(e, moyenne, i + 1);
        }
    }

    // Fonction de recherche d’un élève
    public static void rechercherEleve(Scanner scanner, List<Eleve> eleves) {
        System.out.print("Rechercher par (id, nom, prenom, classe) : ");
        String critere = scanner.nextLine().toLowerCase();

        for (Eleve e : eleves) {
            if (String.valueOf(e.getId()).equals(critere) ||
                e.getNom().equalsIgnoreCase(critere) ||
                e.getPrenom().equalsIgnoreCase(critere) ||
                e.getNiveau().equalsIgnoreCase(critere)) {

                double moyenne = Bulletin.calculerMoyenneGenerale(e);
                Bulletin.imprimerBulletin(e, moyenne, 0);
                return;
            }
        }
        System.out.println("Élève non trouvé.");
    }

    // Modifier les notes d’un élève
    public static void modifierNotes(Scanner scanner, List<Eleve> eleves) {
        System.out.print("ID de l'élève à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Eleve e : eleves) {
            if (e.getId() == id) {
                for (Matiere matiere : Bulletin.getMatieres()) {
                    int nombreNotes = nombreDeNotesParMatiere.getOrDefault(matiere.getNom(), 1);
                    double total = 0;
                    for (int i = 1; i <= nombreNotes; i++) {
                        System.out.print("Nouvelle note " + i + " en " + matiere.getNom() + " : ");
                        total += scanner.nextDouble();
                    }
                    double moyenne = total / nombreNotes;
                    e.ajouterNote(matiere.getNom(), moyenne);
                }
                scanner.nextLine();
                exporterCSV(eleves); // Sauvegarde automatique
                System.out.println("Notes mises à jour.");
                return;
            }
        }
        System.out.println("Élève non trouvé.");
    }

    // Supprimer un élève
    public static void supprimerEleve(Scanner scanner, List<Eleve> eleves) {
        System.out.print("ID de l'élève à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Iterator<Eleve> it = eleves.iterator();
        while (it.hasNext()) {
            Eleve e = it.next();
            if (e.getId() == id) {
                it.remove();
                exporterCSV(eleves); // Sauvegarde automatique
                System.out.println("Élève supprimé.");
                return;
            }
        }
        System.out.println("Élève non trouvé.");
    }

    // Exporter les bulletins en CSV
    public static void exporterCSV(List<Eleve> eleves) {
        File fichier = new File("bulletins.csv");
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichier, false))) { // overwrite
            pw.println("ID,Nom,Prénom,Classe,Mathematique,Physique,Francais,Anglais,Moyenne");
            for (Eleve e : eleves) {
                double moyenne = Bulletin.calculerMoyenneGenerale(e);
                pw.printf("%d,%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f\n",
                        e.getId(), e.getNom(), e.getPrenom(), e.getNiveau(),
                        e.getNote("Mathematique"), e.getNote("Physique"),
                        e.getNote("Francais"), e.getNote("Anglais"), moyenne);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l’export CSV : " + e.getMessage());
        }
    }

    // Charger les élèves depuis le fichier CSV
    public static List<Eleve> chargerDepuisCSV() {
        List<Eleve> eleves = new ArrayList<>();
        File fichier = new File("bulletins.csv");
        if (!fichier.exists()) return eleves;

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne = br.readLine(); // Ignorer l'en-tête
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(",");
                if (parties.length >= 9) {
                    String nom = parties[1];
                    String prenom = parties[2];
                    String niveau = parties[3];
                    Eleve e = new Eleve(nom, prenom, niveau);
                    e.ajouterNote("Mathematique", Double.parseDouble(parties[4]));
                    e.ajouterNote("Physique", Double.parseDouble(parties[5]));
                    e.ajouterNote("Francais", Double.parseDouble(parties[6]));
                    e.ajouterNote("Anglais", Double.parseDouble(parties[7]));
                    eleves.add(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier CSV : " + e.getMessage());
        }
        return eleves;
    }
}

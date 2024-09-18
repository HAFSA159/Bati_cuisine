import Model.EtatProjet;
import Model.Projet;
import Model.Client;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Client> clients = new ArrayList<>();
    private static List<Projet> projets = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
        boolean continuer = true;
        while (continuer) {
            afficherMenuPrincipal();
            int choix = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choix) {
                case 1:
                    creerNouveauProjet();
                    break;
                case 2:
                    afficherProjetsExistants();
                    break;
                case 3:
                    calculerCoutProjet();
                    break;
                case 4:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }

        System.out.println("Merci d'avoir utilisé l'application Bati-Cuisine. Au revoir !");
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n=== Menu Principal ===");
        System.out.println("1. Créer un nouveau projet");
        System.out.println("2. Afficher les projets existants");
        System.out.println("3. Calculer le coût d'un projet");
        System.out.println("4. Quitter");
        System.out.print("Choisissez une option : ");
    }

    private static void creerNouveauProjet() {
        System.out.print("Entrez l'ID du projet : ");
        String id = scanner.nextLine();

        System.out.print("Entrez le nom du projet : ");
        String nomProjet = scanner.nextLine();

        System.out.print("Entrez la surface du projet (en m²) : ");
        double surface = scanner.nextDouble();

        System.out.print("Entrez la marge bénéficiaire (en %) : ");
        double margeBeneficiaire = scanner.nextDouble();

        System.out.print("Entrez le coût total estimé : ");
        double coutTotal = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        System.out.print("Entrez l'état du projet (ENCOURS, TERMINE, ANNULE) : ");
        String etat = scanner.nextLine();
        EtatProjet etatProjet = EtatProjet.valueOf(etat.toUpperCase());

        Projet nouveauProjet = new Projet(id, nomProjet, surface, margeBeneficiaire, coutTotal, etatProjet);
        projets.add(nouveauProjet);
        System.out.println("Projet créé avec succès !");
    }

    private static void afficherProjetsExistants() {
        if (projets.isEmpty()) {
            System.out.println("Aucun projet existant.");
            return;
        }

        System.out.println("\n=== Projets Existants ===");
        for (Projet projet : projets) {
            System.out.println("ID: " + projet.getId() +
                    ", Nom: " + projet.getNomProjet() +
                    ", Surface: " + projet.getSurface() +
                    ", Marge Bénéficiaire: " + projet.getMargeBeneficiaire() +
                    ", Coût Total: " + projet.getCoutTotal() +
                    ", État: " + projet.getEtatProjet());
        }
    }

    private static void calculerCoutProjet() {
        System.out.print("Entrez l'ID du projet pour calculer le coût : ");
        String id = scanner.nextLine();

        Projet projet = trouverProjetParId(id);
        if (projet == null) {
            System.out.println("Projet non trouvé !");
            return;
        }

        System.out.println("Le coût total du projet \"" + projet.getNomProjet() + "\" est : " + projet.getCoutTotal());
    }

    private static Projet trouverProjetParId(String id) {
        for (Projet projet : projets) {
            if (projet.getId().equals(id)) {
                return projet;
            }
        }
        return null;
    }
}

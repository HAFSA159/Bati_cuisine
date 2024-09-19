import Model.EtatProjet;
import Model.Projet;
import DAO.ProjetDAO;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static ProjetDAO projetDAO = new ProjetDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
        boolean continuer = true;
        while (continuer) {
            afficherMenuPrincipal();
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    creerNouveauProjet();
                    break;
                case 2:
                    //afficherProjetsExistants();
                    break;
                case 3:
                    //calculerCoutProjet();
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

        try {
            projetDAO.createProjet(nouveauProjet);
            System.out.println("Projet créé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création du projet : " + e.getMessage());
        }
    }







/*
    private static void afficherProjetsExistants() {
        System.out.println("\n=== Projets Existants ===");
        try {
            List<Projet> projets = projetDAO.getAllProjets();
            if (projets.isEmpty()) {
                System.out.println("Aucun projet existant.");
            } else {
                for (Projet projet : projets) {
                    System.out.println("ID: " + projet.getId() +
                            ", Nom: " + projet.getNomProjet() +
                            ", Surface: " + projet.getSurface() +
                            ", Marge Bénéficiaire: " + projet.getMargeBeneficiaire() +
                            ", Coût Total: " + projet.getCoutTotal() +
                            ", État: " + projet.getEtatProjet());
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des projets : " + e.getMessage());
        }
    }

    private static void calculerCoutProjet() {
        System.out.print("Entrez l'ID du projet pour calculer le coût : ");
        String id = scanner.nextLine();

        try {
            Projet projet = projetDAO.getProjetById(id);
            if (projet == null) {
                System.out.println("Projet non trouvé !");
                return;
            }

            System.out.println("Le coût total du projet \"" + projet.getNomProjet() + "\" est : " + projet.getCoutTotal());
        } catch (SQLException e) {
            System.out.println("Erreur lors du calcul du coût du projet : " + e.getMessage());
        }
    }

 */
}

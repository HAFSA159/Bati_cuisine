import Model.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            scanner.nextLine();

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
        System.out.println("\n--- Création d'un Nouveau Projet ---");

        Client client = rechercherOuCreerClient();

        System.out.print("Entrez le nom du projet : ");
        String nomProjet = scanner.nextLine();

        System.out.print("Entrez la marge bénéficiaire (en %) : ");
        double margeBeneficiaire = scanner.nextDouble();
        scanner.nextLine();

        Projet projet = new Projet(nomProjet, client, margeBeneficiaire);
        projets.add(projet);

        System.out.println("Projet créé avec succès !");
    }

    private static Client rechercherOuCreerClient() {
        System.out.println("--- Recherche de client ---");
        System.out.print("Entrez le nom du client : ");
        String nomClient = scanner.nextLine();

        for (Client client : clients) {
            if (client.getNom().equalsIgnoreCase(nomClient)) {
                System.out.println("Client trouvé !");
                return client;
            }
        }

        System.out.println("Client non trouvé. Création d'un nouveau client.");
        System.out.print("Entrez l'adresse du client : ");
        String adresse = scanner.nextLine();
        System.out.print("Entrez le numéro de téléphone du client : ");
        String telephone = scanner.nextLine();
        System.out.print("Le client est-il un professionnel ? (oui/non) : ");
        boolean estProfessionnel = scanner.nextLine().equalsIgnoreCase("oui");

        Client nouveauClient = new Client(nomClient, adresse, telephone, estProfessionnel);
        clients.add(nouveauClient);
        return nouveauClient;
    }

    private static void afficherProjetsExistants() {
        System.out.println("\n--- Projets Existants ---");
        if (projets.isEmpty()) {
            System.out.println("Aucun projet n'existe pour le moment.");
        } else {
            for (int i = 0; i < projets.size(); i++) {
                Projet projet = projets.get(i);
                System.out.println((i + 1) + ". " + projet.getNomProjet() + " - Client : " + projet.getClient().getNom());
            }
        }
    }

    private static void calculerCoutProjet() {
        System.out.println("\n--- Calcul du Coût d'un Projet ---");
        afficherProjetsExistants();
        if (projets.isEmpty()) {
            return;
        }

        System.out.print("Sélectionnez le numéro du projet : ");
        int choixProjet = scanner.nextInt();
        scanner.nextLine();

        if (choixProjet < 1 || choixProjet > projets.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Projet projetSelectionne = projets.get(choixProjet - 1);
        ajouterComposantsAuProjet(projetSelectionne);

        double coutTotal = projetSelectionne.getComposants().stream()
                .mapToDouble(Composant::calculerCout)
                .sum();

        double coutFinal = coutTotal * (1 + projetSelectionne.getMargeBeneficiaire() / 100);
        projetSelectionne.setCoutTotal(coutFinal);

        System.out.println("Coût total du projet : " + coutFinal + " €");

        creerDevis(projetSelectionne);
    }

    private static void ajouterComposantsAuProjet(Projet projet) {
        boolean continuerAjout = true;
        while (continuerAjout) {
            System.out.println("\nAjouter un composant au projet :");
            System.out.println("1. Matériau");
            System.out.println("2. Main d'œuvre");
            System.out.println("3. Terminer l'ajout de composants");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterMateriau(projet);
                    break;
                case 2:
                    ajouterMainDoeuvre(projet);
                    break;
                case 3:
                    continuerAjout = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void ajouterMateriau(Projet projet) {
        System.out.print("Nom du matériau : ");
        String nom = scanner.nextLine();
        System.out.print("Coût unitaire : ");
        double coutUnitaire = scanner.nextDouble();
        System.out.print("Quantité : ");
        double quantite = scanner.nextDouble();
        System.out.print("Coût de transport : ");
        double coutTransport = scanner.nextDouble();
        System.out.print("Coefficient de qualité : ");
        double coefficientQualite = scanner.nextDouble();
        System.out.print("Taux de TVA (%) : ");
        double tauxTVA = scanner.nextDouble();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Materiau materiau = new Materiau(nom, tauxTVA, coutUnitaire, quantite, coutTransport, coefficientQualite);
        projet.ajouterComposant(materiau);
        System.out.println("Matériau ajouté au projet.");
    }

    private static void ajouterMainDoeuvre(Projet projet) {
        System.out.print("Nom de la main d'œuvre : ");
        String nom = scanner.nextLine();
        System.out.print("Taux horaire : ");
        double tauxHoraire = scanner.nextDouble();
        System.out.print("Nombre d'heures : ");
        double heuresTravail = scanner.nextDouble();
        System.out.print("Productivité (1.0 = standard) : ");
        double productiviteOuvrier = scanner.nextDouble();
        System.out.print("Taux de TVA (%) : ");
        double tauxTVA = scanner.nextDouble();
        scanner.nextLine(); // Consommer la nouvelle ligne

        MainDoeuvre mainDoeuvre = new MainDoeuvre(nom, tauxTVA, tauxHoraire, heuresTravail, productiviteOuvrier);
        projet.ajouterComposant(mainDoeuvre);
        System.out.println("Main d'œuvre ajoutée au projet.");
    }

    private static void creerDevis(Projet projet) {
        System.out.println("\n--- Création du Devis ---");
        System.out.print("Date d'émission du devis (format JJ/MM/AAAA) : ");
        String dateEmissionStr = scanner.nextLine();
        System.out.print("Date de validité du devis (format JJ/MM/AAAA) : ");
        String dateValiditeStr = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateEmission = LocalDate.parse(dateEmissionStr, formatter);
        LocalDate dateValidite = LocalDate.parse(dateValiditeStr, formatter);

        Devis devis = new Devis(projet, dateEmission, dateValidite);
        System.out.println("Devis créé avec succès !");
        System.out.println("Montant estimé : " + devis.getMontantEstime() + " €");
        System.out.println("Valide jusqu'au : " + devis.getDateValidite());
    }
}
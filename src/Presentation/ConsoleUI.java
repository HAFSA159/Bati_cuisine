package Presentation;

import DAO.ClientDAO;
import DAO.ProjetDAO;
import Model.Client;
import Model.ProjectStatus;
import Model.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private ClientDAO clientDAO;
    private Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void startMenu() {
        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Create Client");
            System.out.println("2. Show Clients");
            System.out.println("3. Add Project");
            System.out.println("4. Show Projects");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    showClients();
                    break;
                case 3:
                    addProject();
                    break;
                case 4:
                    showProjects();
                    break;
                case 5:
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createClient() {
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();

        System.out.print("Enter client address: ");
        String address = scanner.nextLine();

        System.out.print("Enter client phone: ");
        String phone = scanner.nextLine();

        System.out.print("Is the client a professional? (true/false): ");
        boolean isProfessional = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        Client client = new Client(name, address, phone, isProfessional);

        try {
            clientDAO.createClient(client);
            System.out.println("Client created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating client: " + e.getMessage());
        }
    }

    private void showClients() {
        try {
            List<Client> clients = clientDAO.getAllClients();
            if (clients.isEmpty()) {
                System.out.println("No clients found.");
            } else {
                for (Client client : clients) {
                    System.out.println("ID: " + client.getId() +
                            ", Name: " + client.getName() +
                            ", Address: " + client.getAddress() +
                            ", Phone: " + client.getPhone() +
                            ", Professional: " + (client.isProfessional() ? "Yes" : "No"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving clients: " + e.getMessage());
        }
    }

    private void addProject() {
        try {
            System.out.print("Enter Project Name: ");
            String projectName = scanner.nextLine();

            System.out.print("Enter Area: ");
            double area = scanner.nextDouble();

            System.out.print("Enter Profit Margin: ");
            double profitMargin = scanner.nextDouble();
            scanner.nextLine();

            ProjectStatus projectStatus = ProjectStatus.IN_PROGRESS;

            Project projet = new Project(projectName, area, profitMargin, projectStatus);

            ProjetDAO.createProject(projet);

            System.out.println("Project added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding project: " + e.getMessage());
        }
    }

    private void showProjects() {
        try {
            List<Project> projects = ProjetDAO.getAllProjects();
            if (projects.isEmpty()) {
                System.out.println("No projects found.");
            } else {
                for (Project project : projects) {
                    System.out.println("ID: " + project.getId() +
                            ", Name: " + project.getProjectName() +
                            ", Surface: " + project.getSurface() +
                            ", Profit Margin: " + project.getProfitMargin() +
                           // ", Total Cost: " + (project.getTotalCost() != null ? project.getTotalCost() : "N/A") +
                            ", Status: " + project.getProjectStatus());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving projects: " + e.getMessage());
        }
    }

}

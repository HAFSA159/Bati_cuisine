package Presentation;

import DAO.Dao_Implementation.ComponentDAO;
import DAO.Dao_Interface.ClientDAOInterface;
import DAO.Dao_Interface.ProjectDAOInterface;
import Model.Client;
import Model.ProjectStatus;
import Model.Project;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;

public class ConsoleUI {

    private Scanner scanner = new Scanner(System.in);
    private ClientDAOInterface clientDAO;
    private ProjectDAOInterface projetDAO;
    private ComponentDAO componentDAO; // Now added as dependency

    public ConsoleUI(ClientDAOInterface clientDAO, ProjectDAOInterface projetDAO, ComponentDAO componentDAO) {
        this.clientDAO = clientDAO;
        this.projetDAO = projetDAO;
        this.componentDAO = componentDAO; // Injected ComponentDAO
    }

    public void startMenu() throws SQLException {
        boolean continueRunning = true;
        while (continueRunning) {
            try {
                System.out.println("\n=== Main Menu ===");
                System.out.println("1. Manage Clients");
                System.out.println("2. Manage Projects");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        manageClients();
                        break;
                    case 2:
                        manageProjects();
                        break;
                    case 3:
                        continueRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void manageClients() {
        boolean continueManaging = true;
        while (continueManaging) {
            try {
                System.out.println("\n=== Manage Clients ===");
                System.out.println("1. Create Client");
                System.out.println("2. Show Clients");
                System.out.println("3. Back to Main Menu");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        createClient();
                        break;
                    case 2:
                        showClients();
                        break;
                    case 3:
                        continueManaging = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void manageProjects() throws SQLException {
        boolean continueManaging = true;
        while (continueManaging) {
            try {
                System.out.println("\n=== Manage Projects ===");
                System.out.println("1. Add Project");
                System.out.println("2. Show Projects");
                System.out.println("3. Update Project");
                System.out.println("4. Delete Project");
                System.out.println("5. Back to Main Menu");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addProjectWithComponents();
                        break;
                    case 2:
                        showProjects();
                        break;
                    case 3:
                        updateProject();
                        break;
                    case 4:
                        deleteProject();
                        break;
                    case 5:
                        continueManaging = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
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
        boolean isProfessional = Boolean.parseBoolean(scanner.nextLine());

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

    private void addProjectWithComponents() throws SQLException {
        System.out.println("\n=== Add Project ===");

        System.out.print("Enter Project Name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter Surface Area: ");
        double surface = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Profit Margin: ");
        double profitMargin = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter Client ID: ");
        int clientId = Integer.parseInt(scanner.nextLine());

        Project project = new Project(projectName, surface, profitMargin, ProjectStatus.IN_PROGRESS, clientId);

        int projectId = projetDAO.createProject(project);

        addMaterialsToProject(projectId);
        addLaborToProject(projectId);

        System.out.println("Project added successfully with all components!");
    }

    private void addMaterialsToProject(int projectId) throws SQLException {
        boolean continueAddingMaterials = true;

        while (continueAddingMaterials) {
            System.out.println("\n=== Add Material ===");
            System.out.print("Enter Material Name: ");
            String materialName = scanner.nextLine();
            System.out.print("Enter VAT Rate: ");
            double materialVATRate = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Unit Cost: ");
            double unitCost = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Quantity: ");
            double quantity = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Transport Cost: ");
            double transportCost = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Quality Coefficient: ");
            double qualityCoefficient = Double.parseDouble(scanner.nextLine());

            componentDAO.addMaterial(materialName, materialVATRate, unitCost, quantity, transportCost, qualityCoefficient, projectId);

            System.out.print("Do you want to add another material? (yes/no): ");
            continueAddingMaterials = scanner.nextLine().equalsIgnoreCase("yes");
        }
    }

    private void addLaborToProject(int projectId) throws SQLException {
        boolean continueAddingLabor = true;

        while (continueAddingLabor) {
            System.out.println("\n=== Add Labor ===");
            System.out.print("Enter Labor Name: ");
            String laborName = scanner.nextLine();
            System.out.print("Enter VAT Rate: ");
            double laborVATRate = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Hourly Rate: ");
            double hourlyRate = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Hours Worked: ");
            double hoursWorked = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Worker Productivity: ");
            double workerProductivity = Double.parseDouble(scanner.nextLine());

            componentDAO.addLabor(laborName, laborVATRate, hourlyRate, hoursWorked, workerProductivity, projectId);

            System.out.print("Do you want to add another labor? (yes/no): ");
            continueAddingLabor = scanner.nextLine().equalsIgnoreCase("yes");
        }
    }

    private void showProjects() {
        try {
            List<Project> projects = projetDAO.getAllProjects();
            if (projects.isEmpty()) {
                System.out.println("No projects found.");
            } else {
                for (Project project : projects) {
                    System.out.println("ID: " + project.getId() +
                            ", Name: " + project.getProjectName() +
                            ", Surface: " + project.getSurface() +
                            ", Profit Margin: " + project.getProfitMargin() +
                            ", Client ID: " + project.getClientId());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving projects: " + e.getMessage());
        }
    }

    private void updateProject() {
        // Implement update project logic
    }

    private void deleteProject() {
        // Implement delete project logic
    }
}
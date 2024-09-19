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
        private Scanner scanner;
        private ClientDAO clientDAO;
        private ProjetDAO projetDAO;

        public ConsoleUI(ClientDAO clientDAO, ProjetDAO projetDAO) {
            this.scanner = new Scanner(System.in);
            this.clientDAO = clientDAO;
            this.projetDAO = projetDAO;
        }

        public void startMenu() {
            boolean continueRunning = true;
            while (continueRunning) {
                System.out.println("\n=== Main Menu ===");
                System.out.println("1. Manage Clients");
                System.out.println("2. Manage Projects");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

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
            }
        }

        private void manageClients() {
            boolean continueManaging = true;
            while (continueManaging) {
                System.out.println("\n=== Manage Clients ===");
                System.out.println("1. Create Client");
                System.out.println("2. Show Clients");
                System.out.println("3. Back to Main Menu");
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
                        continueManaging = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }

        private void manageProjects() {
            boolean continueManaging = true;
            while (continueManaging) {
                System.out.println("\n=== Manage Projects ===");
                System.out.println("1. Add Project");
                System.out.println("2. Show Projects");
                System.out.println("3. Update Project");
                System.out.println("4. Delete Project");
                System.out.println("5. Back to Main Menu");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addProject();
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

    public void updateProject() {
            System.out.println("Enter Project ID to update:");
            int id = Integer.parseInt(scanner.nextLine()); // Changed to int

            try {
                // Retrieve the existing project details
                Project existingProject = projetDAO.getProjectById(id);
                if (existingProject == null) {
                    System.out.println("Project not found.");
                    return;
                }

                System.out.println("Enter new Project Name (leave blank to keep current):");
                String projectName = scanner.nextLine();
                if (!projectName.isEmpty()) {
                    existingProject.setProjectName(projectName);
                }

                System.out.println("Enter new Surface (leave blank to keep current):");
                String surfaceInput = scanner.nextLine();
                if (!surfaceInput.isEmpty()) {
                    existingProject.setSurface(Double.parseDouble(surfaceInput));
                }

                System.out.println("Enter new Profit Margin (leave blank to keep current):");
                String profitMarginInput = scanner.nextLine();
                if (!profitMarginInput.isEmpty()) {
                    existingProject.setProfitMargin(Double.parseDouble(profitMarginInput));
                }

                System.out.println("Enter new Project Status (IN_PROGRESS, COMPLETED, CANCELLED, leave blank to keep current):");
                String projectStatusInput = scanner.nextLine();
                if (!projectStatusInput.isEmpty()) {
                    existingProject.setProjectStatus(ProjectStatus.valueOf(projectStatusInput));
                }

                // Update the project (excluding totalCost)
                projetDAO.updateProjectWithoutCost(existingProject);

                System.out.println("Project updated successfully.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter valid numbers.");
            } catch (SQLException e) {
                System.out.println("Error updating project: " + e.getMessage());
            }
        }

    public void deleteProject() {
            System.out.println("Enter Project ID to delete:");
            int id = Integer.parseInt(scanner.nextLine()); // Changed to int

            try {
                projetDAO.deleteProject(id);
            } catch (SQLException e) {
                System.out.println("Error deleting project: " + e.getMessage());
            }
    }

    }

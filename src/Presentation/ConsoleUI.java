package Presentation;

import Repository.Repository_Implementation.ComponentRepository;
import Repository.Repository_Interface.ClientRepositoryInterface;
import Repository.Repository_Interface.ProjectRepositoryInterface;
import Model.Client;
import Model.ProjectStatus;
import Model.Project;

import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;

public class ConsoleUI {

    private Scanner scanner = new Scanner(System.in);
    private ClientRepositoryInterface clientDAO;
    private ProjectRepositoryInterface projetDAO;
    private ComponentRepository componentDAO; // Now added as dependency

    public ConsoleUI(ClientRepositoryInterface clientDAO, ProjectRepositoryInterface projetDAO, ComponentRepository componentDAO) {
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

// Crud For Client

    private void manageClients() {
        boolean continueManaging = true;
        while (continueManaging) {
            try {
                System.out.println("\n=== Manage Clients ===");
                System.out.println("1. Create Client");
                System.out.println("2. Show Clients");
                System.out.println("3. Update Client");
                System.out.println("4. Delete Client");
                System.out.println("5. Back to Main Menu");
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
                        updateClient();
                        break;
                    case 4:
                        deleteClient();
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

        private void updateClient() {
            try {
                System.out.print("Enter Client ID to update: ");
                String clientId = scanner.nextLine(); // Client ID as String based on your `Client` model

                // Retrieve the client using clientDAO
                Client client = clientDAO.getClientById(Integer.parseInt(clientId));
                if (client == null) {
                    System.out.println("Client not found!");
                    return;
                }

                // Prompt for new Client details, with the option to leave them unchanged
                System.out.print("Enter new Client Name (leave empty to keep current: " + client.getName() + "): ");
                String name = scanner.nextLine();
                if (name.isEmpty()) {
                    name = client.getName();
                }

                System.out.print("Enter new Client Address (leave empty to keep current: " + client.getAddress() + "): ");
                String address = scanner.nextLine();
                if (address.isEmpty()) {
                    address = client.getAddress();
                }

                System.out.print("Enter new Client Phone (leave empty to keep current: " + client.getPhone() + "): ");
                String phone = scanner.nextLine();
                if (phone.isEmpty()) {
                    phone = client.getPhone();
                }

                System.out.print("Is the client a professional? (true/false, leave empty to keep current: " + client.isProfessional() + "): ");
                String professionalInput = scanner.nextLine();
                boolean isProfessional = professionalInput.isEmpty() ? client.isProfessional() : Boolean.parseBoolean(professionalInput);

                // Update the client's details
                client.setName(name);
                client.setAddress(address);
                client.setPhone(phone);
                client.setProfessional(isProfessional);

                // Call the DAO to update the client in the database
                clientDAO.updateClient(client); // Ensure this method is implemented in clientDAO

                System.out.println("Client updated successfully!");
            } catch (SQLException e) {
                System.out.println("Error updating client: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        private void deleteClient() {
            try {
                System.out.print("Enter Client ID to delete: ");
                int clientId = Integer.parseInt(scanner.nextLine());

                boolean isDeleted = clientDAO.deleteClientById(Integer.parseInt(String.valueOf(clientId)));
                if (isDeleted) {
                    System.out.println("Client deleted successfully!");
                } else {
                    System.out.println("Client not found or could not be deleted.");
                }
            } catch (SQLException e) {
                System.out.println("Error deleting client: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

// Crud For Project

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
            try {
                System.out.print("Enter Project ID to update: ");
                int projectId = Integer.parseInt(scanner.nextLine());

                Project project = projetDAO.getProjectById(projectId);
                if (project == null) {
                    System.out.println("Project not found!");
                    return;
                }

                System.out.print("Enter new Project Name (leave empty to keep current: " + project.getProjectName() + "): ");
                String projectName = scanner.nextLine();
                if (projectName.isEmpty()) {
                    projectName = project.getProjectName();
                }

                System.out.print("Enter new Surface Area (leave empty to keep current: " + project.getSurface() + "): ");
                String surfaceInput = scanner.nextLine();
                double surface = surfaceInput.isEmpty() ? project.getSurface() : Double.parseDouble(surfaceInput);

                System.out.print("Enter new Profit Margin (leave empty to keep current: " + project.getProfitMargin() + "): ");
                String profitMarginInput = scanner.nextLine();
                double profitMargin = profitMarginInput.isEmpty() ? project.getProfitMargin() : Double.parseDouble(profitMarginInput);

                project.setProjectName(projectName);
                project.setSurface(surface);
                project.setProfitMargin(profitMargin);

                projetDAO.updateProjectWithoutCost(project);
                System.out.println("Project updated successfully!");
            } catch (SQLException e) {
                System.out.println("Error updating project: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        private void deleteProject() {
            try {
                System.out.print("Enter Project ID to delete: ");
                int projectId = Integer.parseInt(scanner.nextLine());

                // Assuming there's a method in your ProjectRepository to delete a project by ID
                boolean isDeleted = projetDAO.deleteProjectById(projectId);
                if (isDeleted) {
                    System.out.println("Project deleted successfully!");
                } else {
                    System.out.println("Project not found or could not be deleted.");
                }
            } catch (SQLException e) {
                System.out.println("Error deleting project: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

}
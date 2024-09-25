package Presentation;

import Model.Quotation;
import Repository.Repository_Implementation.ComponentRepository;
import Repository.Repository_Implementation.ProjectRepository;
import Repository.Repository_Implementation.QuotationRepository;
import Repository.Repository_Interface.ClientRepositoryInterface;
import Repository.Repository_Interface.ProjectRepositoryInterface;
import Model.Client;
import Model.ProjectStatus;
import Model.Project;
import Utilitaire.DatabaseConnection;
import com.sun.jndi.ldap.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ClientRepositoryInterface clientRepository;
    private final ProjectRepositoryInterface projectRepository;
    private final ComponentRepository componentRepository;
    private final QuotationRepository quotationRepository;

    public ConsoleUI(ClientRepositoryInterface clientDAO,
                     ProjectRepositoryInterface projectDAO,
                     ComponentRepository componentDAO,
                     QuotationRepository quotationRepository) {
        this.clientRepository = clientDAO;
        this.projectRepository = projectDAO;
        this.componentRepository = componentDAO;
        this.quotationRepository = quotationRepository;
    }

    public void startMenu() throws SQLException {
        boolean continueRunning = true;
        while (continueRunning) {
            try {
                System.out.println("\n=== Main Menu ===");
                System.out.println("1. Manage Clients");
                System.out.println("2. Manage Projects");
                System.out.println("3. Display Quotations"); // Add this option
                System.out.println("4. Exit");
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
                        manageQuotations();
                        break;
                    case 4:
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
                clientRepository.createClient(client);
                System.out.println("Client created successfully!");
            } catch (SQLException e) {
                System.out.println("Error creating client: " + e.getMessage());
            }
        }

        private void showClients() {
            try {
                List<Client> clients = clientRepository.getAllClients();
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
                String clientId = scanner.nextLine();

                // Retrieve the client using clientDAO
                Client client = clientRepository.getClientById(Integer.parseInt(clientId));
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

                clientRepository.updateClient(client);

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

                boolean isDeleted = clientRepository.deleteClientById(Integer.parseInt(String.valueOf(clientId)));
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
                System.out.println("5. Search Projects");
                System.out.println("6. Back to Main Menu");
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
                        searchProjects();
                        break;
                    case 6:
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

            int projectId = projectRepository.createProject(project);

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

                componentRepository.addMaterial(materialName, materialVATRate, unitCost, quantity, transportCost, qualityCoefficient, projectId);

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

                componentRepository.addLabor(laborName, laborVATRate, hourlyRate, hoursWorked, workerProductivity, projectId);

                System.out.print("Do you want to add another labor? (yes/no): ");
                continueAddingLabor = scanner.nextLine().equalsIgnoreCase("yes");
            }
        }

        private void searchProjects() throws SQLException {
            System.out.print("Enter Project Name or ID to search: ");
            String input = scanner.nextLine();

            List<Project> projects = projectRepository.getAllProjects();
            List<Project> filteredProjects = projects.stream()
                    .filter(project ->
                            project.getProjectName().toLowerCase().contains(input.toLowerCase()) ||
                                    String.valueOf(project.getId()).equals(input)
                    )
                    .collect(Collectors.toList());

            if (filteredProjects.isEmpty()) {
                System.out.println("No projects found with the name or ID: " + input);
            } else {
                System.out.println("---------------");
                System.out.println("Found Projects:");
                for (Project project : filteredProjects) {
                    System.out.println("Project ID: " + project.getId());
                    System.out.println("Project Name: " + project.getProjectName());
                    System.out.println("Surface Area: " + project.getSurface());
                    System.out.println("Profit Margin: " + project.getProfitMargin());
                    System.out.println("---------------");
                }
            }
        }

        private void showProjects() {
                try {
                    List<Project> projects = projectRepository.getAllProjects();
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

                Project project = projectRepository.getProjectById(projectId);
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

                projectRepository.updateProjectWithoutCost(project);
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

            // Try to delete the project by its ID
            boolean isDeleted = projectRepository.deleteProjectById(projectId);
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

// Crud For Quotation

    private void manageQuotations() {
        boolean continueManaging = true;
        while (continueManaging) {
            try {
                System.out.println("\n=== Manage Quotations ===");
                System.out.println("1. Display All Quotations");
                System.out.println("2. Generate a Quotation");
                System.out.println("3. Update a Quotation");
                System.out.println("4. Delete a Quotation");
                System.out.println("5. Back to Main Menu");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        displayAllQuotations();
                        break;
                    case 2:
                        generateQuotation();
                        break;
                    case 3:
                        updateQuotation();
                        break;
                    case 4:
                        deleteQuotation();
                        break;
                    case 5:
                        continueManaging = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException | SQLException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

        private void generateQuotation() throws SQLException {
            System.out.print("Enter Project ID to generate a quotation: ");
            int projectId;

            try {
                projectId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid project ID.");
                return;
            }

            Project project = projectRepository.getProjectById(projectId);
            if (project == null) {
                System.out.println("Project not found.");
                return;
            }

            double totalMaterialCost = componentRepository.getTotalMaterialCostByProjectId(projectId);
            double totalLaborCost = componentRepository.getTotalLaborCostByProjectId(projectId);

            totalMaterialCost = Math.max(totalMaterialCost, 0);
            totalLaborCost = Math.max(totalLaborCost, 0);

            double totalCost = totalMaterialCost + totalLaborCost;
            double finalCost = totalCost * (1 + project.getProfitMargin());

            double VAT = 15.0; // Set your desired VAT value here

            System.out.println("\n=== Project Quotation ===");
            System.out.println("Project Name: " + project.getProjectName());
            System.out.println("Surface Area: " + project.getSurface() + " sqm");
            System.out.println("Profit Margin: " + (project.getProfitMargin() * 100) + "%");
            System.out.printf("Total Material Cost:  €%.2f%n", totalMaterialCost);
            System.out.printf("Total Labor Cost:  €%.2f%n", totalLaborCost);
            System.out.printf("Final Project Cost (with profit):  €%.2f%n", finalCost);

            // Retrieve the next available ID for the quotation
            QuotationRepository quotationRepository = new QuotationRepository(); // Create instance here
            int nextQuotationId = quotationRepository.getNextQuotationId(); // Call the instance method

            // Create a Quotation object
            Quotation quotation = new Quotation(
                    nextQuotationId, // Use the generated positive ID here
                    finalCost,
                    new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30)), // validity date (30 days later)
                    VAT,
                    false,
                    project
            );

            quotationRepository.saveQuotation(quotation);
            System.out.println("Quotation saved successfully.");
        }

        private void updateQuotation() throws SQLException {
            System.out.print("Enter Quotation ID to update: ");
            int quotationId;

            try {
                quotationId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid quotation ID.");
                return;
            }

            // Récupérer l'instance de QuotationRepository
            QuotationRepository quotationRepository = new QuotationRepository();

            // Récupérer la quotation existante
            Quotation existingQuotation = quotationRepository.getQuotationById(quotationId);
            if (existingQuotation == null) {
                System.out.println("Quotation not found.");
                return;
            }

            // Récupérer l'instance de ProjectRepository (ou la classe appropriée)
            ProjectRepository projectRepository = new ProjectRepository();

            // Demander à l'utilisateur de saisir le nouvel ID du projet
            System.out.print("Enter new Project ID (current: " + existingQuotation.getProjectId() + "): ");
            int projectId = Integer.parseInt(scanner.nextLine());

            // Vérifier si le projet existe
            Project project = projectRepository.getProjectById(projectId);
            if (project == null) {
                System.out.println("Project not found.");
                return;
            }

            // Demander les nouveaux détails
            System.out.print("Enter new Estimated Amount (current: " + existingQuotation.getEstimatedAmount() + "): ");
            double estimatedAmount = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter new VAT (current: " + existingQuotation.getVAT() + "): ");
            double VAT = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter new Accepted status (true/false, current: " + existingQuotation.isAccepted() + "): ");
            boolean accepted = Boolean.parseBoolean(scanner.nextLine());

            // Mettre à jour la quotation
            existingQuotation.setEstimatedAmount(estimatedAmount);
            existingQuotation.setVAT(VAT);
            existingQuotation.setAccepted(accepted);
            existingQuotation.setProject(project); // Mettre à jour le projet

            quotationRepository.updateQuotation(existingQuotation);
            System.out.println("Quotation updated successfully.");
        }

        private void deleteQuotation() throws SQLException {
            System.out.print("Enter Quotation ID to delete: ");
            int quotationId;

            try {
                quotationId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid quotation ID.");
                return;
            }

            // Récupérer l'instance de QuotationRepository
            QuotationRepository quotationRepository = new QuotationRepository();

            // Vérifier si la quotation existe
            if (quotationRepository.getQuotationById(quotationId) == null) {
                System.out.println("Quotation not found.");
                return;
            }

            // Supprimer la quotation
            quotationRepository.deleteQuotation(quotationId);
            System.out.println("Quotation deleted successfully.");
        }

        public void displayAllQuotations() {
        try {
            List<Quotation> quotations = quotationRepository.getAllQuotations();
            for (Quotation quotation : quotations) {
                System.out.println("Quotation ID: " + quotation.getId());
                System.out.println("Estimated Amount: " + quotation.getEstimatedAmount());
                System.out.println("VAT: " + quotation.getVAT());
                System.out.println("Issue Date: " + quotation.getIssueDate());
                System.out.println("Validity Date: " + quotation.getValidityDate());

                if (quotation.getProject() != null) {
                    System.out.println("Project ID: " + quotation.getProject().getId());
                    System.out.println("Project Name: " + quotation.getProject().getProjectName());
                } else {
                    System.out.println("Project: Not available");
                }

                System.out.println("---------------");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching quotations: " + e.getMessage());
        }
    }

}



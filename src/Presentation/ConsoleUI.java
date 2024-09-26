package Presentation;

import Model.Quotation;
import Model.Client;
import Model.ProjectStatus;
import Model.Project;
import Service.Service_Interface.ClientService;
import Service.Service_Interface.ComponentService;
import Service.Service_Interface.ProjectService;
import Service.Service_Interface.QuotationService;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ClientService clientService;
    private final ProjectService projectService;
    private final ComponentService componentService;
    private final QuotationService quotationService;

    public ConsoleUI(ClientService clientService, ProjectService projectService, ComponentService componentService, QuotationService quotationService) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.componentService = componentService;
        this.quotationService = quotationService;
    }

    public void startMenu() throws SQLException {
        boolean continueRunning = true;
        while (continueRunning) {
            try {
                System.out.println("\n=== Main Menu ===");
                System.out.println("1. Manage Clients");
                System.out.println("2. Manage Projects");
                System.out.println("3. Display Quotations");
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

        String phone;
        while (true) {
            System.out.print("Enter client phone (format: +212 followed by 9 digits): ");
            phone = scanner.nextLine();

            if (phone.matches("^\\+212\\d{9}$")) {
                break;
            } else {
                System.out.println("Invalid phone number. Please enter a phone number in the format +212 followed by 9 digits.");
            }
        }

        System.out.print("Is the client a professional? (true/false): ");
        boolean isProfessional = Boolean.parseBoolean(scanner.nextLine());

        Client client = new Client(name, address, phone, isProfessional);

        try {
            clientService.createClient(client);
            System.out.println("Client created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating client: " + e.getMessage());
        }
    }

        private void showClients() {
            try {
                List<Client> clients = clientService.getAllClients();
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

                Client client = clientService.getClientById(Integer.parseInt(clientId));
                if (client == null) {
                    System.out.println("Client not found!");
                    return;
                }

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

                clientService.updateClient(client);

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

                boolean isDeleted = clientService.deleteClientById(Integer.parseInt(String.valueOf(clientId)));
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

            int projectId = projectService.createProject(project);

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

                componentService.addMaterial(materialName, materialVATRate, unitCost, quantity, transportCost, qualityCoefficient, projectId);

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

                componentService.addLabor(laborName, laborVATRate, hourlyRate, hoursWorked, workerProductivity, projectId);

                System.out.print("Do you want to add another labor? (yes/no): ");
                continueAddingLabor = scanner.nextLine().equalsIgnoreCase("yes");
            }
        }

        private void searchProjects() throws SQLException {
            System.out.print("Enter Project Name or ID to search: ");
            String input = scanner.nextLine();

            List<Project> projects = projectService.getAllProjects();
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
                    List<Project> projects = projectService.getAllProjects();
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

                Project project = projectService.getProjectById(projectId);
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

                projectService.updateProjectWithoutCost(project);
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

            boolean isDeleted = projectService.deleteProjectById(projectId);
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
                int choice = getIntInput("Choose an option: ");

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
                        break;
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

        private void generateQuotation() throws SQLException {
            int projectId = getIntInput("Enter Project ID to generate a quotation: ");

            Project project = projectService.getProjectById(projectId);
            if (project == null) {
                System.out.println("Project not found.");
                return;
            }

            double totalMaterialCost = componentService.getTotalMaterialCostByProjectId(projectId);
            double totalLaborCost = componentService.getTotalLaborCostByProjectId(projectId);

            totalMaterialCost = Math.max(totalMaterialCost, 0);
            totalLaborCost = Math.max(totalLaborCost, 0);

            double totalCost = totalMaterialCost + totalLaborCost;
            double finalCost = totalCost * (1 + project.getProfitMargin());

            double VAT = 15.0;

            System.out.println("\n=== Project Quotation ===");
            System.out.println("Project Name: " + project.getProjectName());
            System.out.println("Surface Area: " + project.getSurface() + " sqm");
            System.out.println("Profit Margin: " + (project.getProfitMargin() * 100) + "%");
            System.out.printf("Total Material Cost: €%.2f%n", totalMaterialCost);
            System.out.printf("Total Labor Cost: €%.2f%n", totalLaborCost);
            System.out.printf("Final Project Cost (with profit): €%.2f%n", finalCost);

            int nextQuotationId = quotationService.getNextQuotationId();

            Quotation quotation = new Quotation(
                    nextQuotationId,
                    finalCost,
                    new Date(),
                    new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)), // validity date (30 days later)
                    VAT,
                    false,
                    project
            );

            quotationService.saveQuotation(quotation);
            System.out.println("Quotation saved successfully.");
        }

        private void updateQuotation() throws SQLException {
            int quotationId = getIntInput("Enter Quotation ID to update: ");

            Quotation existingQuotation = quotationService.getQuotationById(quotationId);
            if (existingQuotation == null) {
                System.out.println("Quotation not found.");
                return;
            }

            int projectId = getIntInput("Enter new Project ID (current: " + existingQuotation.getProjectId() + "): ");

            Project project = projectService.getProjectById(projectId);
            if (project == null) {
                System.out.println("Project not found.");
                return;
            }

            double estimatedAmount = getDoubleInput("Enter new Estimated Amount (current: " + existingQuotation.getEstimatedAmount() + "): ");
            double VAT = getDoubleInput("Enter new VAT (current: " + existingQuotation.getVAT() + "): ");
            boolean accepted = getBooleanInput("Enter new Accepted status (true/false, current: " + existingQuotation.isAccepted() + "): ");

            existingQuotation.setEstimatedAmount(estimatedAmount);
            existingQuotation.setVAT(VAT);
            existingQuotation.setAccepted(accepted);
            existingQuotation.setProject(project);

            quotationService.updateQuotation(existingQuotation);
            System.out.println("Quotation updated successfully.");
        }

        private void deleteQuotation() throws SQLException {
            int quotationId = getIntInput("Enter Quotation ID to delete: ");

            if (quotationService.getQuotationById(quotationId) == null) {
                System.out.println("Quotation not found.");
                return;
            }

            quotationService.deleteQuotation(quotationId);
            System.out.println("Quotation deleted successfully.");
        }

        public void displayAllQuotations() throws SQLException {
            List<Quotation> quotations = quotationService.getAllQuotations();
            if (quotations.isEmpty()) {
                System.out.println("No quotations found.");
                return;
            }
            for (Quotation quotation : quotations) {
                displayQuotation(quotation);
            }
        }

        private void displayQuotation(Quotation quotation) {
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

        private int getIntInput(String prompt) {
            while (true) {
                try {
                    System.out.print(prompt);
                    Scanner scanner = new Scanner(System.in);
                    return scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }
        }

        private double getDoubleInput(String prompt) {
            while (true) {
                try {
                    System.out.print(prompt);
                    Scanner scanner = new Scanner(System.in);
                    return scanner.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid decimal number.");
                }
            }
        }

        private boolean getBooleanInput(String prompt) {
            while (true) {
                try {
                    System.out.print(prompt + " (true/false): ");
                    Scanner scanner = new Scanner(System.in);
                    return scanner.nextBoolean();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter 'true' or 'false'.");
                }
            }
        }



}



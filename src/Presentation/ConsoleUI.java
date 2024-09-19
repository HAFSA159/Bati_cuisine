package Presentation;

import DAO.ClientDAO;
import Model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private ClientDAO clientDAO;
    private Scanner scanner;

    public ConsoleUI(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
        this.scanner = new Scanner(System.in);
    }

    public void startMenu() {
        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Create Client");
            System.out.println("2. Show Clients");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    showClients();
                    break;
                case 3:
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

}

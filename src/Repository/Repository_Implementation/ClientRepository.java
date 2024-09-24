package Repository.Repository_Implementation;

import Repository.Repository_Interface.ClientRepositoryInterface;
import Model.Client;
import Utilitaire.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements ClientRepositoryInterface {
    private static final String INSERT_CLIENT = "INSERT INTO Client (name, address, phone, isProfessional) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_CLIENTS = "SELECT * FROM Client";
    private static final String UPDATE_CLIENT = "UPDATE Client SET name = ?, address = ?, phone = ?, isProfessional = ? WHERE id = ?";
    private static final String DELETE_CLIENT = "DELETE FROM Client WHERE id = ?";

    @Override
    public void createClient(Client client) throws SQLException {
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENT)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getAddress());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setBoolean(4, client.isProfessional());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_CLIENTS)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id"); // Changed from String to int for id
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                boolean isProfessional = resultSet.getBoolean("isProfessional");

                // Create a new Client object with int id
                clients.add(new Client(id, name, address, phone, isProfessional));
            }
        }
        return clients;
    }
    public Client getClientById(int clientId) throws SQLException {
        String query = "SELECT * FROM client WHERE id = ?";
        Client client = null;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                client = new Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBoolean("isProfessional")
                );
            }
        }

        return client;
    }

    @Override
    public void updateClient(Client client) throws SQLException {
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getAddress());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setBoolean(4, client.isProfessional());
            preparedStatement.setInt(5, client.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public boolean deleteClientById(int clientId) throws SQLException {

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(DELETE_CLIENT)) {
            stmt.setInt(1, clientId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}

package DAO.Dao_Implementation;

import DAO.Dao_Interface.ClientDAOInterface;
import Model.Client;
import Utilitaire.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements ClientDAOInterface {
    private static final String INSERT_CLIENT = "INSERT INTO Client (name, address, phone, isProfessional) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_CLIENTS = "SELECT * FROM Client";

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
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                boolean isProfessional = resultSet.getBoolean("isProfessional");

                clients.add(new Client(id, name, address, phone, isProfessional));
            }
        }
        return clients;
    }
}

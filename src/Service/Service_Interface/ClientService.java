package Service.Service_Interface;

import Model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    void createClient(Client client) throws SQLException;
    List<Client> getAllClients() throws SQLException;
    Client getClientById(int clientId) throws SQLException;
    void updateClient(Client client) throws SQLException;
    boolean deleteClientById(int clientId) throws SQLException;
}

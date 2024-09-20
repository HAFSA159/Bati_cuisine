package DAO.Dao_Interface;

import Model.Client;
import java.sql.SQLException;
import java.util.List;

public interface ClientDaoInterface {
    void createClient(Client client) throws SQLException;
    List<Client> getAllClients() throws SQLException;
}

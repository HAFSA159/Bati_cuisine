package Service.Service_Implementation;

import Model.Client;
import Repository.Repository_Interface.ClientRepositoryInterface;
import Service.Service_Interface.ClientService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final ClientRepositoryInterface clientRepository;

    public ClientServiceImpl(ClientRepositoryInterface clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public void createClient(Client client) throws SQLException {
        clientRepository.createClient(client);
    }

    @Override
    public List<Client> getAllClients() throws SQLException {
        return clientRepository.getAllClients();
    }

    @Override
    public Client getClientById(int clientId) throws SQLException {
        return clientRepository.getClientById(clientId);
    }

    @Override
    public void updateClient(Client client) throws SQLException {
        clientRepository.updateClient(client);
    }

    @Override
    public boolean deleteClientById(int clientId) throws SQLException {
        return clientRepository.deleteClientById(clientId);
    }
}

import Repository.Repository_Implementation.ClientRepository;
import Repository.Repository_Implementation.ComponentRepository;
import Repository.Repository_Implementation.QuotationRepository;
import Repository.Repository_Interface.ClientRepositoryInterface;
import Repository.Repository_Implementation.ProjectRepository;
import Repository.Repository_Interface.ProjectRepositoryInterface;
import Presentation.ConsoleUI;
import Service.Service_Implementation.ClientServiceImpl;
import Service.Service_Implementation.ComponentServiceImpl;
import Service.Service_Implementation.ProjectServiceImpl;
import Service.Service_Implementation.QuotationServiceImpl;
import Service.Service_Interface.ClientService;
import Service.Service_Interface.ComponentService;
import Service.Service_Interface.ProjectService;
import Service.Service_Interface.QuotationService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {


        ClientRepositoryInterface clientRepositoryInterface = new ClientRepository();
        ClientService clientService = new ClientServiceImpl(clientRepositoryInterface);

        ProjectRepositoryInterface projectRepositoryInterface = new ProjectRepository();
        ProjectService projectService = new ProjectServiceImpl(projectRepositoryInterface);

        ComponentRepository componentRepositoryInterface = new ComponentRepository();
        ComponentService componentService = new ComponentServiceImpl(componentRepositoryInterface);

        QuotationRepository quotationRepositoryInterface = new QuotationRepository();
        QuotationService quotationService = new QuotationServiceImpl(quotationRepositoryInterface);

        ConsoleUI consoleUI = new ConsoleUI(clientService, projectService, componentService, quotationService);
        consoleUI.startMenu();
    }
}
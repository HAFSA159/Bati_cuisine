import Repository.Repository_Implementation.ClientRepository;
import Repository.Repository_Implementation.ComponentRepository;
import Repository.Repository_Interface.ClientRepositoryInterface;
import Repository.Repository_Implementation.ProjectRepository;
import Repository.Repository_Interface.ProjectRepositoryInterface;
import Presentation.ConsoleUI;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ClientRepositoryInterface clientDAO = new ClientRepository();
        ProjectRepositoryInterface projetDAO = new ProjectRepository();
        ComponentRepository componentDAO = new ComponentRepository();

        ConsoleUI consoleUI = new ConsoleUI(clientDAO, projetDAO, componentDAO);
        consoleUI.startMenu();
    }
}

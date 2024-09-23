import DAO.Dao_Implementation.ClientDAO;
import DAO.Dao_Implementation.ComponentDAO;
import DAO.Dao_Interface.ClientDAOInterface;
import DAO.Dao_Implementation.ProjectDAO;
import DAO.Dao_Interface.ProjectDAOInterface;
import Presentation.ConsoleUI;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ClientDAOInterface clientDAO = new ClientDAO();
        ProjectDAOInterface projetDAO = new ProjectDAO();
        ComponentDAO componentDAO = new ComponentDAO();

        ConsoleUI consoleUI = new ConsoleUI(clientDAO, projetDAO, componentDAO);
        consoleUI.startMenu();
    }
}

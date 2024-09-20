import DAO.Dao_Implementation.ClientDAO;
import DAO.Dao_Interface.ClientDaoInterface;
import DAO.Dao_Implementation.ProjetDAO;
import DAO.Dao_Interface.ProjetDAOInterface;
import Presentation.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        ClientDaoInterface clientDAO = new ClientDAO();
        ProjetDAOInterface projetDAO = new ProjetDAO();

        ConsoleUI consoleUI = new ConsoleUI(clientDAO, projetDAO);
        consoleUI.startMenu();
    }
}

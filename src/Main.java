import DAO.ClientDAO;
import DAO.ProjetDAO;
import Presentation.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        ClientDAO clientDAO = new ClientDAO();
        ProjetDAO projetDAO = new ProjetDAO();

        ConsoleUI consoleUI = new ConsoleUI(clientDAO, projetDAO);
        consoleUI.startMenu();
    }
}

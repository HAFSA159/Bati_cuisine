import DAO.ClientDAO;
import Presentation.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        ClientDAO clientDAO = new ClientDAO();
        ConsoleUI consoleUI = new ConsoleUI(clientDAO);
        consoleUI.startMenu();
    }
}

package DAO.Dao_Implementation;

import DAO.Dao_Interface.ProjetDAOInterface;
import Model.Project;
import Model.ProjectStatus;
import Utilitaire.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetDAO implements ProjetDAOInterface {

    @Override
    public void createProject(Project projet) throws SQLException {
        String INSERT_PROJECT = "INSERT INTO project (projectName, surface, profitMargin, totalCost, projectStatus, clientId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT)) {
            preparedStatement.setString(1, projet.getProjectName());
            preparedStatement.setDouble(2, projet.getSurface());
            preparedStatement.setDouble(3, projet.getProfitMargin());
            preparedStatement.setObject(4, projet.getTotalCost(), Types.DOUBLE);
            preparedStatement.setString(5, projet.getProjectStatus().name());
            preparedStatement.setString(6, projet.getClientId()); // Set clientId here
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Project> getAllProjects() throws SQLException {
        String SELECT_ALL_PROJECTS = "SELECT * FROM project";
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_PROJECTS)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String projectName = resultSet.getString("projectName");
                double surface = resultSet.getDouble("surface");
                double profitMargin = resultSet.getDouble("profitMargin");
                Double totalCost = resultSet.getObject("totalCost", Double.class);
                ProjectStatus projectStatus = ProjectStatus.valueOf(resultSet.getString("projectStatus"));
                String clientId = resultSet.getString("clientId");

                projects.add(new Project(id, projectName, surface, profitMargin, totalCost, projectStatus, clientId));
            }
        }
        return projects;
    }

    @Override
    public void updateProjectWithoutCost(Project project) throws SQLException {
        String UPDATE_PROJECT = "UPDATE project SET projectName = ?, surface = ?, profitMargin = ?, projectStatus = ?, clientId = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PROJECT)) {
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setDouble(2, project.getSurface());
            preparedStatement.setDouble(3, project.getProfitMargin());
            preparedStatement.setString(4, project.getProjectStatus().name());
            preparedStatement.setString(5, project.getClientId()); // Include clientId in the update
            preparedStatement.setInt(6, project.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Project getProjectById(int id) throws SQLException {
        String SELECT_PROJECT_BY_ID = "SELECT * FROM project WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROJECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String projectName = resultSet.getString("projectName");
                    double surface = resultSet.getDouble("surface");
                    double profitMargin = resultSet.getDouble("profitMargin");
                    Double totalCost = resultSet.getObject("totalCost", Double.class);
                    ProjectStatus projectStatus = ProjectStatus.valueOf(resultSet.getString("projectStatus"));
                    String clientId = resultSet.getString("clientId"); // Retrieve clientId

                    return new Project(id, projectName, surface, profitMargin, totalCost, projectStatus, clientId);
                } else {
                    return null; // Project not found
                }
            }
        }
    }

    @Override
    public void deleteProject(int id) throws SQLException {
        String DELETE_PROJECT = "DELETE FROM project WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROJECT)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No project found with ID: " + id);
            } else {
                System.out.println("Project deleted successfully.");
            }
        }
    }
}

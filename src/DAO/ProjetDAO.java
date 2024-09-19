package DAO;

import Model.Project;
import Model.ProjectStatus;
import Utilitaire.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetDAO {

    public static void createProject(Project projet) throws SQLException {
        String INSERT_PROJECT = "INSERT INTO project (projectName, surface, profitMargin, totalCost, projectStatus) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT)) {
            preparedStatement.setString(1, projet.getProjectName());
            preparedStatement.setDouble(2, projet.getSurface());
            preparedStatement.setDouble(3, projet.getProfitMargin());

            if (projet.getTotalCost() != null) {
                preparedStatement.setDouble(4, projet.getTotalCost());
            } else {
                preparedStatement.setNull(4, Types.DOUBLE);
            }

            preparedStatement.setString(5, projet.getProjectStatus().name()); // Assuming ProjectStatus is an enum
            preparedStatement.executeUpdate();
        }
    }

    public static List<Project> getAllProjects() throws SQLException {
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

                projects.add(new Project(id, projectName, surface, profitMargin, totalCost, projectStatus));
            }
        }
        return projects;
    }

    public void updateProjectWithoutCost(Project project) throws SQLException {
        String UPDATE_PROJECT = "UPDATE project SET projectName = ?, surface = ?, profitMargin = ?, projectStatus = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PROJECT)) {
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setDouble(2, project.getSurface());
            preparedStatement.setDouble(3, project.getProfitMargin());
            preparedStatement.setString(4, project.getProjectStatus().name()); // Assuming ProjectStatus is an enum
            preparedStatement.setInt(5, project.getId()); // Use setInt for ID
            preparedStatement.executeUpdate();
        }
    }


    public static void deleteProject(int projectId) throws SQLException {
        String DELETE_PROJECT = "DELETE FROM project WHERE id = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROJECT)) {
            preparedStatement.setInt(1, projectId);
            preparedStatement.executeUpdate();
        }
    }

    public Project getProjectById(int id) throws SQLException {
        String SELECT_PROJECT_BY_ID = "SELECT * FROM project WHERE id = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROJECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve data from the ResultSet
                    String projectName = resultSet.getString("projectName");
                    double surface = resultSet.getDouble("surface");
                    double profitMargin = resultSet.getDouble("profitMargin");
                    Double totalCost = resultSet.getObject("totalCost", Double.class); // Handle possible NULL
                    ProjectStatus projectStatus = ProjectStatus.valueOf(resultSet.getString("projectStatus"));

                    // Create and return the Project object
                    return new Project(id, projectName, surface, profitMargin, totalCost, projectStatus);
                } else {
                    // Project not found
                    return null;
                }
            }
        }
    }

}


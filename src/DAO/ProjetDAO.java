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
}

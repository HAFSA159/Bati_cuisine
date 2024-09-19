package DAO;

import Model.ProjectStatus;
import Model.Project;
import Utilitaire.DatabaseConnection;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

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



}

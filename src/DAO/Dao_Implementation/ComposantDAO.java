package DAO.Dao_Implementation;

import Model.Composant;
import Model.MainDoeuvre;
import Model.Materiau;
import Utilitaire.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComposantDAO {

    public void addComposantToProject(Composant composant, long projectId) throws SQLException {
        String INSERT_COMPOSANT = "INSERT INTO project_composant (project_id, composant_id, type_composant, quantite, cout_unitaire, taux_horaire, heures_travail) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = ((java.sql.Connection) connection).prepareStatement(INSERT_COMPOSANT)) {

            preparedStatement.setLong(1, projectId); // Set the project ID
            preparedStatement.setString(2, composant.getId()); // Composant ID
            preparedStatement.setString(3, composant.getTypeComposant()); // Type of composant ('MATERIEL' or 'MAINDOEUVRE')

            if (composant instanceof Materiau) {
                Materiau materiau = (Materiau) composant;
                preparedStatement.setDouble(4, materiau.getQuantite()); // Quantity for Materiau
                preparedStatement.setDouble(5, materiau.getCoutUnitaire()); // Unit cost for Materiau
                preparedStatement.setNull(6, java.sql.Types.DOUBLE); // No hourly rate for Materiau
                preparedStatement.setNull(7, java.sql.Types.DOUBLE); // No work hours for Materiau
            } else if (composant instanceof MainDoeuvre) {
                MainDoeuvre mainDoeuvre = (MainDoeuvre) composant;
                preparedStatement.setNull(4, java.sql.Types.DOUBLE); // No quantity for MainDoeuvre
                preparedStatement.setNull(5, java.sql.Types.DOUBLE); // No unit cost for MainDoeuvre
                preparedStatement.setDouble(6, mainDoeuvre.getTauxHoraire()); // Hourly rate for MainDoeuvre
                preparedStatement.setDouble(7, mainDoeuvre.getHeuresTravail()); // Work hours for MainDoeuvre
            }

            // Execute the insertion
            preparedStatement.executeUpdate();
        }
    }

}

package Repository.Repository_Implementation;

import Model.Quotation;
import Repository.Repository_Interface.QuotationRepositoryInterface;
import Utilitaire.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class QuotationRepository implements QuotationRepositoryInterface {

    // Correct usage in your saveQuotation method
    public void saveQuotation(Quotation quotation) {
        String sql = "INSERT INTO quotation (id, estimatedAmount, issueDate, validityDate, VAT, accepted, projectId) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, quotation.getId());
            preparedStatement.setDouble(2, quotation.getEstimatedAmount());
            preparedStatement.setDate(3, new java.sql.Date(quotation.getIssueDate().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(quotation.getValidityDate().getTime()));
            preparedStatement.setDouble(5, quotation.getVAT());
            preparedStatement.setBoolean(6, quotation.isAccepted());
            preparedStatement.setInt(7, quotation.getProjectId());

            preparedStatement.executeUpdate();
            System.out.println("Quotation saved successfully!");
        } catch (SQLException e) {
            System.out.println("Error saving quotation: " + e.getMessage());
        }
    }
}
package Repository.Repository_Implementation;

import Model.Project;
import Model.ProjectStatus;
import Model.Quotation;
import Repository.Repository_Interface.QuotationRepositoryInterface;
import Utilitaire.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuotationRepository implements QuotationRepositoryInterface {


    @Override
    public void saveQuotation(Quotation quotation) throws SQLException {
        String sql = "INSERT INTO quotation (estimatedAmount, issueDate, validityDate, VAT, accepted, projectId) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setDouble(1, quotation.getEstimatedAmount());
            preparedStatement.setDate(2, new java.sql.Date(quotation.getIssueDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(quotation.getValidityDate().getTime()));
            preparedStatement.setDouble(4, quotation.getVAT());
            preparedStatement.setBoolean(5, quotation.isAccepted());
            preparedStatement.setInt(6, quotation.getProjectId());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    quotation.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saving quotation: " + e.getMessage());
            throw e; // Re-throw to handle it further up
        }
    }

    public void updateQuotation(Quotation quotation) throws SQLException {
        String sql = "UPDATE quotation SET estimatedAmount = ?, issueDate = ?, validityDate = ?, VAT = ?, accepted = ?, projectId = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDouble(1, quotation.getEstimatedAmount());
            preparedStatement.setDate(2, new java.sql.Date(quotation.getIssueDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(quotation.getValidityDate().getTime()));
            preparedStatement.setDouble(4, quotation.getVAT());
            preparedStatement.setBoolean(5, quotation.isAccepted());
            preparedStatement.setInt(6, quotation.getProjectId());
            preparedStatement.setInt(7, quotation.getId());

            preparedStatement.executeUpdate();
            System.out.println("Quotation updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating quotation: " + e.getMessage());
            throw e;
        }
    }

    public void deleteQuotation(int id) throws SQLException {
        String sql = "DELETE FROM quotation WHERE id = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Quotation deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting quotation: " + e.getMessage());
            throw e;
        }
    }

    public Quotation getQuotationById(int id) throws SQLException {
        String sql = "SELECT * FROM quotation WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ProjectRepository projectRepository = new ProjectRepository(); // Ensure you handle this correctly

                double VAT = resultSet.getDouble("VAT");

                return new Quotation(
                        resultSet.getInt("id"),
                        resultSet.getDouble("estimatedAmount"),
                        resultSet.getDate("issueDate"),
                        resultSet.getDate("validityDate"),
                        VAT,
                        resultSet.getBoolean("accepted"),
                        projectRepository.getProjectById(resultSet.getInt("projectId"))
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving quotation: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public List<Quotation> getAllQuotations() throws SQLException {
        List<Quotation> quotations = new ArrayList<>();
        String sql = "SELECT * FROM quotation";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Quotation quotation = new Quotation(
                        rs.getInt("id"),
                        rs.getDouble("estimatedamount"),
                        rs.getDate("issuedate"),
                        rs.getDate("validitydate"),
                        rs.getDouble("vat"),
                        rs.getBoolean("accepted"),
                        getProjectById(rs.getInt("projectid"))
                );
                quotations.add(quotation);
            }
        }
        return quotations;
    }

    public int getNextQuotationId() {
        String sql = "SELECT MAX(id) AS maxId FROM quotation";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("maxId") + 1;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving next quotation ID: " + e.getMessage());
        }
        return 1; // Default if no quotations exist
    }

    public Project getProjectById(int projectId) throws SQLException {
        Project project = null;
        String sql = "SELECT * FROM project WHERE id = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                project = new Project(
                        rs.getInt("id"),
                        rs.getString("projectname"),
                        rs.getDouble("surface"),
                        rs.getDouble("profitmargin"),
                        rs.getDouble("totalcost"),
                        ProjectStatus.valueOf(rs.getString("projectstatus")),
                        rs.getString("clientid")
                );
            }
        }
        return project;
    }




}



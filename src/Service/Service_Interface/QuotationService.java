package Service.Service_Interface;

import Model.Project;
import Model.Quotation;

import java.sql.SQLException;
import java.util.List;

public interface QuotationService {
    void saveQuotation(Quotation quotation) throws SQLException;
    Quotation getQuotationById(int id) throws SQLException;
    List<Quotation> getAllQuotations() throws SQLException;
    Project getProjectById(int projectId) throws SQLException;
    void updateQuotation(Quotation quotation) throws SQLException;
    void deleteQuotation(int id) throws SQLException;
    int getNextQuotationId() throws SQLException;
}

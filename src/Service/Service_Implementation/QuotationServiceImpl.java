package Service.Service_Implementation;

import Model.Project;
import Model.Quotation;
import Repository.Repository_Implementation.QuotationRepository;
import Service.Service_Interface.QuotationService;

import java.sql.SQLException;
import java.util.List;

public class QuotationServiceImpl implements QuotationService {
    public final QuotationRepository quotationRepository;

    public QuotationServiceImpl(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    @Override
    public void saveQuotation(Quotation quotation) throws SQLException {
        quotationRepository.saveQuotation(quotation);
    }

    @Override
    public Quotation getQuotationById(int id) throws SQLException {
        return quotationRepository.getQuotationById(id);
    }

    @Override
    public List<Quotation> getAllQuotations() throws SQLException {
        return quotationRepository.getAllQuotations();
    }

    @Override
    public Project getProjectById(int projectId) throws SQLException {
        return quotationRepository.getProjectById(projectId);
    }

    @Override
    public void updateQuotation(Quotation quotation) throws SQLException {
        quotationRepository.updateQuotation(quotation);
    }

    @Override
    public void deleteQuotation(int id) throws SQLException {
        quotationRepository.deleteQuotation(id);
    }

    @Override
    public int getNextQuotationId() throws SQLException {
        return quotationRepository.getNextQuotationId();
    }



}

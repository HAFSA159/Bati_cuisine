package Repository.Repository_Interface;

import Model.Quotation;
import java.sql.SQLException;

public interface QuotationRepositoryInterface {
    void saveQuotation(Quotation quotation) throws SQLException;

}

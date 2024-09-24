package Repository.Repository_Interface;

import java.sql.SQLException;

public interface ComponentRepositoryInterface {
    void addMaterial(String name, double VATRate, double unitCost, double quantity, double transportCost, double qualityCoefficient, int projectId) throws SQLException;
    void addLabor(String name, double VATRate, double hourlyRate, double hoursWorked, double workerProductivity, int projectId) throws SQLException;
}

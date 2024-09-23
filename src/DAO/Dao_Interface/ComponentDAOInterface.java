package DAO.Dao_Interface;

import java.sql.SQLException;

public interface ComponentDAOInterface {
    void addMaterial(String name, double VATRate, double unitCost, double quantity, double transportCost, double qualityCoefficient, int projectId) throws SQLException;
    void addLabor(String name, double VATRate, double hourlyRate, double hoursWorked, double workerProductivity, int projectId) throws SQLException;
}

package Service.Service_Interface;

import Model.Project;

import java.sql.SQLException;
import java.util.List;

public interface ComponentService {
    void addMaterial(String name, double VATRate, double unitCost, double quantity, double transportCost, double qualityCoefficient, int projectId) throws SQLException;
    void addLabor(String name, double VATRate, double hourlyRate, double hoursWorked, double workerProductivity, int projectId) throws SQLException;
    double getTotalMaterialCostByProjectId(int projectId) throws SQLException;
    double getTotalLaborCostByProjectId(int projectId) throws SQLException;
}

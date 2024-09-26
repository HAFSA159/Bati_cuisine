package Service.Service_Implementation;

import Repository.Repository_Implementation.ComponentRepository;
import Service.Service_Interface.ComponentService;

import java.sql.SQLException;


public class ComponentServiceImpl implements ComponentService {
    public final ComponentRepository componentRepository;

    public ComponentServiceImpl(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }


    @Override
    public void addMaterial(String name, double VATRate, double unitCost, double quantity, double transportCost, double qualityCoefficient, int projectId) throws SQLException {
        componentRepository.addMaterial(name, VATRate, unitCost, quantity, transportCost,qualityCoefficient, projectId);
    }

    @Override
    public void addLabor(String name, double VATRate, double hourlyRate, double hoursWorked, double workerProductivity, int projectId) throws SQLException {
        componentRepository.addLabor(name, VATRate, hourlyRate, hoursWorked, workerProductivity, projectId);
    }

    @Override
    public double getTotalMaterialCostByProjectId(int projectId) throws SQLException {
        return componentRepository.getTotalMaterialCostByProjectId(projectId);
    }

    @Override
    public double getTotalLaborCostByProjectId(int projectId) throws SQLException {
        return componentRepository.getTotalLaborCostByProjectId(projectId);
    }
}

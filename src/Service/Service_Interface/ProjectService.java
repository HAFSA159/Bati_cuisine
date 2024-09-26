package Service.Service_Interface;

import Model.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectService {
    int createProject(Project project) throws SQLException;
    List<Project> getAllProjects() throws SQLException;
    void updateProjectWithoutCost(Project project) throws SQLException;
    Project getProjectById(int id) throws SQLException;
    boolean deleteProjectById(int id) throws SQLException;

}

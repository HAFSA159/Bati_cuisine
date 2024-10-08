package Repository.Repository_Interface;

import Model.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectRepositoryInterface {
    int createProject(Project projet) throws SQLException;
    List<Project> getAllProjects() throws SQLException;
    void updateProjectWithoutCost(Project project) throws SQLException;
    Project getProjectById(int id) throws SQLException;
    boolean deleteProjectById(int id) throws SQLException;

}

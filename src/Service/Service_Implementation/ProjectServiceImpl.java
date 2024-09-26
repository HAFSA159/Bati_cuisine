package Service.Service_Implementation;

import Model.Project;
import Repository.Repository_Interface.ProjectRepositoryInterface;
import Service.Service_Interface.ProjectService;

import java.sql.SQLException;
import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepositoryInterface projectRepository;

    public ProjectServiceImpl(ProjectRepositoryInterface projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public int createProject(Project project) throws SQLException {
        return projectRepository.createProject(project);
    }

    @Override
    public List<Project> getAllProjects() throws SQLException {
        return projectRepository.getAllProjects();
    }

    @Override
    public void updateProjectWithoutCost(Project project) throws SQLException {
        projectRepository.updateProjectWithoutCost(project);
    }

    @Override
    public Project getProjectById(int id) throws SQLException {
        return projectRepository.getProjectById(id);
    }

    @Override
    public boolean deleteProjectById(int id) throws SQLException {
        return projectRepository.deleteProjectById(id);
    }
}

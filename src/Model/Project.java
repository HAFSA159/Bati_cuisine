package Model;

public class Project {
    private int id;
    private String projectName;
    private double surface;
    private double profitMargin;
    private Double totalCost; // Nullable
    private ProjectStatus projectStatus;
    private String clientId;

    // Constructor with all parameters
    public Project(int id, String projectName, double surface, double profitMargin, Double totalCost, ProjectStatus projectStatus, String clientId) {
        this.id = id;
        this.projectName = projectName;
        this.surface = surface;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.projectStatus = projectStatus;
        this.clientId = clientId;
    }

    public Project(String projectName, double surface, double profitMargin, ProjectStatus projectStatus, int clientId) {
        this(0, projectName, surface, profitMargin, null, projectStatus, String.valueOf(clientId));
    }

    // Constructor without totalCost and clientId
    public Project(String projectName, double surface, double profitMargin, ProjectStatus projectStatus) {
        this(0, projectName, surface, profitMargin, null, projectStatus, null); // id is defaulted to 0, clientId is null
    }

    // Constructor with clientId
    public Project(String projectName, double surface, double profitMargin, ProjectStatus projectStatus, String clientId) {
        this(0, projectName, surface, profitMargin, null, projectStatus, clientId); // id is defaulted to 0
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

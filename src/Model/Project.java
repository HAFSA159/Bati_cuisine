package Model;

public class Project {
    private int id; // Use int for auto-incremented IDs
    private String projectName;
    private double surface;
    private double profitMargin;
    private Double totalCost; // Nullable
    private ProjectStatus projectStatus;

    // Constructor with all fields
    public Project(int id, String projectName, double surface, double profitMargin, Double totalCost, ProjectStatus projectStatus) {
        this.id = id;
        this.projectName = projectName;
        this.surface = surface;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.projectStatus = projectStatus;
    }

    // Constructor without totalCost
    public Project(String projectName, double surface, double profitMargin, ProjectStatus projectStatus) {
        this(0, projectName, surface, profitMargin, null, projectStatus); // id is defaulted to 0
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
}

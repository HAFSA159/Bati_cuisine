package Model;

import java.util.Date;

public class Quotation {
    private int id;
    private double estimatedAmount;
    private Date issueDate;
    private Date validityDate;
    private double VAT;
    private boolean accepted;
    private Project project; // Keeping the Project object

    public Quotation(int id, double estimatedAmount, Date issueDate, Date validityDate, double VAT, boolean accepted, Project project) {
        if (id <= 0) {
            throw new IllegalArgumentException("Quotation ID must be positive.");
        }
        if (estimatedAmount < 0) {
            throw new IllegalArgumentException("Estimated amount cannot be negative.");
        }
        if (VAT < 0 || VAT > 100) {
            throw new IllegalArgumentException("VAT must be between 0 and 100.");
        }
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }

        this.id = id;
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.VAT = VAT;
        this.accepted = accepted;
        this.project = project; // Initialize project
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Quotation ID must be positive.");
        }
        this.id = id;
    }

    public double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(double estimatedAmount) {
        if (estimatedAmount < 0) {
            throw new IllegalArgumentException("Estimated amount cannot be negative.");
        }
        this.estimatedAmount = estimatedAmount;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        if (VAT < 0 || VAT > 100) {
            throw new IllegalArgumentException("VAT must be between 0 and 100.");
        }
        this.VAT = VAT;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Project getProject() {
        return project;
    }

    // Getter for project ID from the Project object
    public int getProjectId() {
        return this.project.getId(); // Assuming Project has a method getId()
    }

    // Setter for Project object
    public void setProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }
        this.project = project; // Update the project
    }
}

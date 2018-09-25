package imaginers.com.taskfire.Model;

public class Project {
    private String projectID;
    private String projectName;
    private String projectType;

    public Project() {
    }

    public Project(String projectID, String projectName, String projectType) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectType = projectType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}

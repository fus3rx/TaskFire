package imaginers.com.taskfire.Model;

public class Task {
    private String taskID;
    private String taskName;
    private String taskType;

    public Task() {
    }

    public Task(String taskID, String taskName, String taskType) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskType = taskType;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}

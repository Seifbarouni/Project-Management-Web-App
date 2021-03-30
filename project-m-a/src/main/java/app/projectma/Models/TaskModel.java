package app.projectma.Models;

public class TaskModel {
    private Long id;
    private Long projectId;
    private String description;
    private Boolean isTask;
    private Boolean isInProgress;
    private Boolean isInReview;
    private Boolean isDone;

    public TaskModel(Long projectId, String description, Boolean isTask, Boolean isInProgress, Boolean isInReview,
            Boolean isDone) {
        this.projectId = projectId;
        this.description = description;
        this.isTask = isTask;
        this.isInProgress = isInProgress;
        this.isInReview = isInReview;
        this.isDone = isDone;
    }

    public TaskModel() {

    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public Boolean getIsInProgress() {
        return isInProgress;
    }

    public Boolean getIsInReview() {
        return isInReview;
    }

    public Boolean getIsTask() {
        return isTask;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public void setIsInProgress(Boolean isInProgress) {
        this.isInProgress = isInProgress;
    }

    public void setIsInReview(Boolean isInReview) {
        this.isInReview = isInReview;
    }

    public void setIsTask(Boolean isTask) {
        this.isTask = isTask;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}

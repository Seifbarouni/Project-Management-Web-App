package app.tasksmicroservice.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskModel {
    @Id
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "is_task", nullable = false)
    private Boolean isTask;
    @Column(name = "is_in_progress", nullable = false)
    private Boolean isInProgress;
    @Column(name = "is_in_review", nullable = false)
    private Boolean isInReview;
    @Column(name = "is_done", nullable = false)
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

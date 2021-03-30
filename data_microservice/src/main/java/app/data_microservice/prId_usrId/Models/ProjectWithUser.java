package app.data_microservice.prId_usrId.Models;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;

@Entity
@Table(name = "pr_with_usr")
public class ProjectWithUser {
    @Id
    @SequenceGenerator(name = "prws_sequence", sequenceName = "prws_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prws_sequence")
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    public ProjectWithUser(Long pid, Long uid) {
        this.projectId = pid;
        this.userId = uid;
    }

    public ProjectWithUser() {

    }

    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}

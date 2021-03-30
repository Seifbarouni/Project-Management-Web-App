package app.data_microservice.Projects.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @SequenceGenerator(name = "project_sequence", sequenceName = "project_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_sequence")
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "admin_username", nullable = false)
    private String adminUsername;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "description", nullable = false)
    private String description;

    public Project(String admin, String title, String desc, String type) {
        this.adminUsername = admin;
        this.title = title;
        this.description = desc;
        this.type = type;
    }

    public Project() {

    }

    public Long getId() {
        return id;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

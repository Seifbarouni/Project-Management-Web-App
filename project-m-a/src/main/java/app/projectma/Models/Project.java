package app.projectma.Models;

public class Project {
    private Long id;
    private String adminUsername;
    private String title;
    private String type;
    private String description;
    private Long numberOfCollaborators;

    public Project(String admin, String title, String desc, String type) {
        this.adminUsername = admin;
        this.title = title;
        this.type = type;
        this.description = desc;
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

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Long getNumberOfCollaborators() {
        return numberOfCollaborators;
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

    public void setNumberOfCollaborators(Long numberOfCollaborators) {
        this.numberOfCollaborators = numberOfCollaborators;
    }

}

package app.projectma.Models;

public class Note {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String important;
    private String createdAt;

    public Note(Long id, Long userId, String title, String description, String important, String createdAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.important = important;
    }

    public Note() {
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getImportant() {
        return important;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

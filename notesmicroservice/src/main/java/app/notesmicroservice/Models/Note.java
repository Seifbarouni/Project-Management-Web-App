package app.notesmicroservice.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @SequenceGenerator(name = "note_sequence", sequenceName = "note_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_sequence")
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "important", nullable = false)
    private String important;
    @Column(name = "created_at", nullable = false)
    private String createdAt;

    public Note(Long userId, String title, String description, String important, String createdAt) {
        this.createdAt = createdAt;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.important = important;
    }

    public Note() {
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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
}

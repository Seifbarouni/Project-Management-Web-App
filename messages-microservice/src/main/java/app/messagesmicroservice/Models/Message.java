package app.messagesmicroservice.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @SequenceGenerator(name = "message_sequence", sequenceName = "message_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_sequence")
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "sender", nullable = false)
    private String sender;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "time", nullable = false)
    private String time;
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    public Message(String sender, String content, String time, Long projectId) {
        this.sender = sender;
        this.content = content;
        this.time = time;
        this.projectId = projectId;
    }

    public Message() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

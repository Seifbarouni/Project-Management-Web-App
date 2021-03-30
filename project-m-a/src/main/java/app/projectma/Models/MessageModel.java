package app.projectma.Models;

public class MessageModel {
    private String content;
    private String sender;
    private String time;
    private Long projectId;

    public MessageModel(String sender, String content, String time, Long projectId) {
        this.sender = sender;
        this.content = content;
        this.time = time;
        this.projectId = projectId;
    }

    public MessageModel() {
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return this.time;
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

}

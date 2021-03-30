package app.projectma.Models;

public class Message {

    private MessageType type;
    private String content;
    private String sender;
    private String time;
    private Long projectId;

    public Message() {
    }

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    public MessageType getType() {
        return this.type;
    }

    public void setType(MessageType type) {
        this.type = type;
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
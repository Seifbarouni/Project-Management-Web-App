package app.projectma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import app.projectma.Models.Message;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/chat.send/{projectId}")
    public Message sendMessage(@DestinationVariable String projectId, @Payload Message message) {
        this.template.convertAndSend("/topic/" + projectId, message);
        return message;
    }

    @MessageMapping("/chat.newUser/{projectId}")
    public Message newUser(@DestinationVariable String projectId, @Payload Message message,
            SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        this.template.convertAndSend("/topic/" + projectId, message);
        return message;
    }

}

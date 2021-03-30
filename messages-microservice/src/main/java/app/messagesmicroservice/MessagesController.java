package app.messagesmicroservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.messagesmicroservice.Models.Message;
import app.messagesmicroservice.Services.MessagesService;

@RestController
@RequestMapping("/messages")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;

    @GetMapping("/getMessages/{projectId}")
    @Cacheable(value = "messagesByProjectId", key = "#root.args[0]")
    public List<Message> getMessages(@PathVariable(name = "projectId") Long projectId) {
        return messagesService.getMessages(projectId);
    }

    @PostMapping("/addMessage")
    @CacheEvict(value = "messagesByProjectId", key = "#root.args[0].projectId")
    @CrossOrigin(origins = "http://localhost:8080")
    public String addMessage(@RequestBody Message message) {
        return messagesService.addMessage(message);
    }

}

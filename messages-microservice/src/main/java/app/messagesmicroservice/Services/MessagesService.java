package app.messagesmicroservice.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.messagesmicroservice.Data.MessagesRepository;
import app.messagesmicroservice.Models.Message;

@Service
public class MessagesService {
    @Autowired
    private MessagesRepository messagesRepository;

    public List<Message> getMessages(Long projectId) {
        Optional<List<Message>> res = messagesRepository.findByProjectId(projectId);
        if (res.isPresent()) {
            return res.get();
        }
        return new ArrayList<Message>();
    }

    public String addMessage(Message message) {
        Message res = messagesRepository.saveAndFlush(message);
        if (res != null) {
            return "Success";
        }
        return "Cannot add message";
    }
}

package app.messagesmicroservice.Data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.messagesmicroservice.Models.Message;

public interface MessagesRepository extends JpaRepository<Message, Long> {
    Optional<List<Message>> findByProjectId(Long projectId);
}

package app.notesmicroservice.Data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.notesmicroservice.Models.Note;

public interface NotesRepository extends JpaRepository<Note, Long> {
    Optional<List<Note>> findByUserId(Long userId);
}

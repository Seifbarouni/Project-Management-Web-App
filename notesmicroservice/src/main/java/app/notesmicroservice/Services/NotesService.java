package app.notesmicroservice.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.notesmicroservice.Data.NotesRepository;
import app.notesmicroservice.Models.Note;

@Service
public class NotesService {
    @Autowired
    private NotesRepository notesRepo;

    public List<Note> getNotesByUserId(Long userId) {
        Optional<List<Note>> res = notesRepo.findByUserId(userId);
        if (res.isPresent()) {
            return res.get();
        }
        return new ArrayList<Note>();
    }

    public String addNote(Note note) {
        Note res = notesRepo.save(note);
        if (res != null) {
            return "Success";
        }
        return "Cannot add note";
    }

    public String updateNote(Note note) {
        if (notesRepo.existsById(note.getId())) {
            notesRepo.saveAndFlush(note);
            return "Success";
        }
        return "Cannot find note";
    }

    public String deleteNote(Long id) {
        if (notesRepo.existsById(id)) {
            notesRepo.deleteById(id);
            return "Success";
        }
        return "Cannot find note";
    }
}

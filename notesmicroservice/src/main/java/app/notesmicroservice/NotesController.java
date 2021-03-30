package app.notesmicroservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.notesmicroservice.Models.Note;
import app.notesmicroservice.Services.NotesService;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @GetMapping("/getNotes/{userId}")
    @Cacheable(value = "notesByUsId", key = "#root.args[0]")
    public List<Note> getNotes(@PathVariable(name = "userId") Long userId) {
        if (userId != 0) {
            return notesService.getNotesByUserId(userId);
        }
        return new ArrayList<Note>();
    }

    @PutMapping("/updateNote")
    @CrossOrigin(origins = "http://localhost:8080")
    @CacheEvict(value = "notesByUsId", key = "#root.args[0].userId")
    public String updateNote(@RequestBody Note note) {
        return notesService.updateNote(note);
    }

    @PostMapping("/addNote")
    @CrossOrigin(origins = "http://localhost:8080")
    @CacheEvict(value = "notesByUsId", key = "#root.args[0].userId")
    public String addNote(@RequestBody Note note) {
        return notesService.addNote(note);
    }

    @DeleteMapping("/deleteNote/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    @CacheEvict(value = "notesByUsId", allEntries = true)
    public String deleteNote(@PathVariable(name = "id") Long id) {
        return notesService.deleteNote(id);
    }
}

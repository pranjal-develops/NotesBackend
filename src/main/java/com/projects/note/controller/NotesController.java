package com.projects.note.controller;

import com.projects.note.dto.NoteDTO;
import com.projects.note.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {
    @Autowired
    private NotesService noteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteDTO> getAll(@RequestParam(required = false) String q,
                                @RequestParam(required = false) String tag) {
        if (tag != null && !tag.isBlank()) {
            return noteService.getByTag(tag);
        }
        if (q == null || q.isBlank()) {
            return noteService.getAllNotes();
        }
        return noteService.search(q);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return noteService.getNoteById(id).map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDTO create(@RequestBody NoteDTO noteDTO) {
        return noteService.createNote(noteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
        try {
            return ResponseEntity.ok(noteService.updateNote(id, noteDTO));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        noteService.deleteNote(id);
    }

}
